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
 
 
/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2001-2005 Julian Hyde
// Copyright (C) 2005-2011 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap;

import org.apache.log4j.Logger;

import java.util.Locale;
import java.util.Map;

/**
 * <code>OlapElementBase</code> is an abstract base class for implementations of
 * {@link OlapElement}.
 *
 * @author jhyde
 * @since 6 August, 2001
 */
public abstract class OlapElementBase
    implements OlapElement
{
    protected String caption = null;

    protected boolean visible = true;

    // cache hash-code because it is often used and elements are immutable
    private int hash;

    protected OlapElementBase() {
    }

    protected abstract Logger getLogger();

    public boolean equals(Object o) {
        return (o == this)
           || ((o instanceof OlapElement)
               && equals((OlapElement) o));
    }

    public boolean equals(OlapElement mdxElement) {
        return mdxElement != null
           && getClass() == mdxElement.getClass()
           && getUniqueName().equalsIgnoreCase(mdxElement.getUniqueName());
    }

    public int hashCode() {
        if (hash == 0) {
            hash = computeHashCode();
        }
        return hash;
    }

    /**
     * Computes this object's hash code. Called at most once.
     *
     * @return hash code
     */
    protected int computeHashCode() {
        return (getClass().hashCode() << 8) ^ getUniqueName().hashCode();
    }

    public String toString() {
        return getUniqueName();
    }

    public Object clone() {
        return this;
    }

    /**
     * Returns the display name of this catalog element.
     * If no caption is defined, the name is returned.
     */
    public String getCaption() {
        if (caption != null) {
            return caption;
        } else {
            return getName();
        }
    }

    /**
     * Sets the display name of this catalog element.
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    public boolean isVisible() {
        return visible;
    }

    public String getLocalized(LocalizedProperty prop, Locale locale) {
        if (this instanceof Annotated) {
            Annotated annotated = (Annotated) this;
            final Map<String, Annotation> annotationMap =
                annotated.getAnnotationMap();
            if (!annotationMap.isEmpty()) {
                String seek = prop.name().toLowerCase() + "." + locale;
                for (;;) {
                    for (Map.Entry<String, Annotation> entry
                        : annotationMap.entrySet())
                    {
                        if (entry.getKey().startsWith(seek)) {
                            return entry.getValue().getValue().toString();
                        }
                    }

                    // No match for locale. Is there a match for the parent
                    // locale? For example, we've just looked for
                    // 'caption.en_US', now look for 'caption.en'.
                    final int underscore = seek.lastIndexOf('_');
                    if (underscore < 0) {
                        break;
                    }
                    seek = seek.substring(0, underscore - 1);
                }
            }
        }

        // No annotation. Fall back to the default caption/description.
        switch (prop) {
        case CAPTION:
            return getCaption();
        case DESCRIPTION:
            return getDescription();
        default:
            throw Util.unexpected(prop);
        }
    }
}

// End OlapElementBase.java
