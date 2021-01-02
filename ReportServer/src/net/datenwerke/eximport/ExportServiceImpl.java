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
 
 
package net.datenwerke.eximport;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.datenwerke.eximport.ex.ExportConfig;
import net.datenwerke.eximport.ex.ExportSupervisor;
import net.datenwerke.eximport.ex.ExportSupervisorFactory;
import net.datenwerke.eximport.ex.Exporter;
import net.datenwerke.eximport.hooks.ExporterProviderHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.google.inject.Inject;

/**
 * 
 *
 */
public class ExportServiceImpl implements ExportService {

	private final ExImportHelperService eiHelper;
	private final ExportSupervisorFactory exportSupervisorFactory;
	private final HookHandlerService hookHandler;
	
	@Inject
	public ExportServiceImpl(
		ExImportHelperService eiHelper,
		ExportSupervisorFactory exportSupervisorFactory,
		HookHandlerService hookHandler
		){
		
		/* store objects */
		this.eiHelper = eiHelper;
		this.exportSupervisorFactory = exportSupervisorFactory;
		this.hookHandler = hookHandler;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.datenwerke.eximport.ExportService#export(net.datenwerke.eximport.ex.ExportConfig)
	 */
	@Override
	public String export(ExportConfig config) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			exportAsStream(config, os);
		
			return os.toString();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.datenwerke.eximport.ExportService#exportIndent(net.datenwerke.eximport.ex.ExportConfig)
	 */
	@Override
	public String exportIndent(ExportConfig config) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			exportAsStream(config, os);
			
			TransformerFactory factory = TransformerFactory.newInstance();

			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			transformer.transform(new StreamSource(new ByteArrayInputStream(os.toByteArray())),new StreamResult(bout)); 
			
			return bout.toString();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public void exportAsStream(ExportConfig config, OutputStream os) throws XMLStreamException{
		XMLOutputFactory xmlOf = XMLOutputFactory.newInstance();
		XMLStreamWriter xsw = xmlOf.createXMLStreamWriter(os, "UTF-8");

		xsw.writeStartDocument();
		
		eiHelper.addNamespaces(xsw);
		
		xsw.writeStartElement(ExImportHelperService.DOCUMENT_ROOT_ELEMENT);
		eiHelper.writeExImportNamespace(xsw);
		
		/* head */
		xsw.writeStartElement(ExImportHelperService.DOCUMENT_HEAD_ELEMENT);
		
		xsw.writeStartElement(ExImportHelperService.DOCUMENT_HEAD_NAME_ELEMENT);
		xsw.writeCharacters(config.getName());
		xsw.writeEndElement();
		
		xsw.writeStartElement(ExImportHelperService.DOCUMENT_HEAD_DESCRIPTION_ELEMENT);
		xsw.writeCharacters(config.getDescription());
		xsw.writeEndElement();
		
		xsw.writeStartElement(ExImportHelperService.DOCUMENT_HEAD_DATE_ELEMENT);
		xsw.writeCharacters((new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss Z")).format(config.getDate()));
		xsw.writeEndElement();
		
		xsw.writeStartElement(ExImportHelperService.DOCUMENT_HEAD_VERSION_ELEMENT);
		xsw.writeEndElement();
		
		xsw.writeEndElement();
		/* end head */
		
		/* main document */
		List<Exporter> exporters = getExporters();
		
		/* create supervisor and start actual export operation */
		ExportSupervisor supervisor = exportSupervisorFactory.create(config, exporters,  xsw);
		supervisor.export();
		
		/* close doc */
		xsw.writeEndElement();
		
		xsw.flush();
        xsw.close();
	}

	private List<Exporter> getExporters() {
		List<Exporter> exporters = new ArrayList<Exporter>();
		
		for(ExporterProviderHook hooker : hookHandler.getHookers(ExporterProviderHook.class))
			exporters.add(hooker.getObject());
		
		return exporters;
	}

	@Override
	public Collection<String> getExporterIds(Collection<Class<?>> exporterTypes) {
		List<String> ids = new ArrayList<String>();
		
		for(Class<?> type : exporterTypes){
			for(Exporter exporter : getExporters()){
				if(type.equals(exporter.getClass())){
					ids.add(exporter.getExporterId());
					break;
				}
			}
		}
		
		return ids;
	}

	/*
	 * (non-Javadoc)
	 * @see net.datenwerke.eximport.ExportService#getExporterFor(java.lang.Class)
	 */
	@Override
	public Exporter getExporterFor(Class<?> exporterType) {
		for(Exporter exporter : getExporters()){
			if(exporterType.equals(exporter.getClass())){
				return exporter;
			}
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.datenwerke.eximport.ExportService#getExporterFor(java.lang.Object)
	 */
	@Override
	public Exporter getExporterFor(Object object) {
		for(Exporter exporter : getExporters())
			if(exporter.consumes(object))
				return exporter;
		return null;
	}
	
	
}
