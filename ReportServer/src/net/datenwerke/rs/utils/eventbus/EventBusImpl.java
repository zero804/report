/*
 *  ReportServer
 *  Copyright (c) 2007 - 2020 InfoFabrik GmbH
 *  http://reportserver.net/
 *
 *
 * This file is part of ReportServer.
 *
 * ReportServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package net.datenwerke.rs.utils.eventbus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class EventBusImpl implements EventBus {

	private final Map<Class<? extends Event>, Collection<EventHandler>> eventHandlers = new ConcurrentHashMap<Class<? extends Event>, Collection<EventHandler>>(); 
	
	private final Map<Class<? extends ObjectEvent>, Map<Class<?>, Collection<EventHandler>>> objectEventHandlers = new ConcurrentHashMap<Class<? extends ObjectEvent>, Map<Class<?>,Collection<EventHandler>>>();
	
	@Inject
	protected Injector injector;

	@Override
	public <E extends ObjectEvent> void attachObjectEventHandler(Class<E> eventType, Class<?> objectType, EventHandler<E> handler) {
		if(! objectEventHandlers.containsKey(eventType))
			objectEventHandlers.put(eventType, new HashMap<Class<?>, Collection<EventHandler>>());
		
		Map<Class<?>, Collection<EventHandler>> entityToCallbacks = objectEventHandlers.get(eventType);
		if(! entityToCallbacks.containsKey(objectType))
			entityToCallbacks.put(objectType, new HashSet<EventHandler>());
		
		Collection<EventHandler> handlers = entityToCallbacks.get(objectType);
		handlers.add(handler);
	}
	
	@Override
	public <E extends ObjectEvent> void detachObjectEventHandler(Class<E> eventType, Class<?> objectType, EventHandler<E> handler) {
		if(objectEventHandlers.containsKey(eventType)){
			Map<Class<?>, Collection<EventHandler>> entityToCallbacks = objectEventHandlers.get(eventType);
			if(entityToCallbacks.containsKey(objectType))
				entityToCallbacks.get(objectType).remove(handler);
			
			if(entityToCallbacks.get(objectType).isEmpty())
				entityToCallbacks.remove(objectType);
			
			if(entityToCallbacks.isEmpty())
				objectEventHandlers.remove(eventType);
		}
	}
	
	@Override
	public <E extends Event> void attachEventHandler(Class<E> eventType, EventHandler<E> handler) {
		if(! eventHandlers.containsKey(eventType))
			eventHandlers.put(eventType, new HashSet<EventHandler>());
		eventHandlers.get(eventType).add(handler);
	}

	@Override
	public <E extends Event> boolean detachEventHandler(Class<E> eventType, EventHandler<E> handler) {
		if(eventHandlers.containsKey(eventType))
			return eventHandlers.get(eventType).remove(handler);
		return false;
	}

	@Override
	public void fireEvent(Event event) {
		if(null == event)
			return;
		
		injector.injectMembers(event);
		
		List<Class<?>> eventTypesToCheck = getEventTypesFor(event);
		
		if(event instanceof ObjectEvent){
			if(null != ((ObjectEvent)event).getObject()){
				Class<?> objectType = ((ObjectEvent)event).getObject().getClass();
				
				Set<EventHandler> processedEventHandlers = new HashSet<EventHandler>();
				for(Class<?> eventType : eventTypesToCheck){
					if(objectEventHandlers.containsKey(eventType)){
						for(Entry<Class<?>, Collection<EventHandler>> handlerEntry : objectEventHandlers.get(eventType).entrySet()){
							if(handlerEntry.getKey().isAssignableFrom(objectType)){
								for(EventHandler handler : handlerEntry.getValue()){
									if(! processedEventHandlers.contains(handler)){
										handler.handle(event);
										processedEventHandlers.add(handler);
									}
								}
							}
						}
					}
					
					eventType = eventType.getSuperclass();
				}
			}
		}
		
		/* additionally .. normal event handlers */
		for(Class<?> eventType : eventTypesToCheck){
			if(eventHandlers.containsKey(eventType))
				for(EventHandler handler : new ArrayList<EventHandler>(eventHandlers.get(eventType)))
					handler.handle(event);
		}
	}

	private List<Class<?>> getEventTypesFor(Event event) {
		List<Class<?>> types = new ArrayList<Class<?>>();
		getEventTypesFor(event.getClass(), types);
		return types;
	}

	private void getEventTypesFor(Class<?> eventType, List<Class<?>> types) {
		types.add(eventType);
		
		for(Class<?> iface : eventType.getInterfaces()){
			if(Event.class.isAssignableFrom(iface))
				getEventTypesFor(iface, types);
		}
		
		if(null != eventType.getSuperclass())
			getEventTypesFor(eventType.getSuperclass(), types);
	}

}
