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
// Copyright (C) 2005-2012 Pentaho and others
// All Rights Reserved.
*/
package mondrian3.olap;

import mondrian3.resource.MondrianResource;

import java.util.List;

/**
 * Abstract implementation for a {@link Dimension}.
 *
 * @author jhyde
 * @since 6 August, 2001
 */
public abstract class DimensionBase
    extends OlapElementBase
    implements Dimension
{
    protected final String name;
    protected final String uniqueName;
    protected final String description;
    protected final boolean highCardinality;
    protected Hierarchy[] hierarchies;
    protected DimensionType dimensionType;

    /**
     * Creates a DimensionBase.
     *
     * @param name Name
     * @param dimensionType Type
     * @param highCardinality Whether high-cardinality
     */
    protected DimensionBase(
        String name,
        String caption,
        boolean visible,
        String description,
        DimensionType dimensionType,
        boolean highCardinality)
    {
        this.name = name;
        this.caption = caption;
        this.visible = visible;
        this.uniqueName = Util.makeFqName(name);
        this.description = description;
        this.dimensionType = dimensionType;
        this.highCardinality = highCardinality;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Hierarchy[] getHierarchies() {
        return hierarchies;
    }

    public Hierarchy getHierarchy() {
        return hierarchies[0];
    }

    public Dimension getDimension() {
        return this;
    }

    public DimensionType getDimensionType() {
        return dimensionType;
    }

    public String getQualifiedName() {
        return MondrianResource.instance().MdxDimensionName.str(
            getUniqueName());
    }

    public boolean isMeasures() {
        return getUniqueName().equals(MEASURES_UNIQUE_NAME);
    }

    public OlapElement lookupChild(
        SchemaReader schemaReader, Id.Segment s, MatchType matchType)
    {
        OlapElement oe = null;
        if (s instanceof Id.NameSegment) {
            oe = lookupHierarchy((Id.NameSegment) s);
        }

        // Original mondrian behavior:
        // If the user is looking for [Marital Status].[Marital Status] we
        // should not return oe "Marital Status", because he is
        // looking for level - we can check that by checking of hierarchy and
        // dimension name is the same.
        //
        if (!MondrianProperties.instance().SsasCompatibleNaming.get()) {
            if (oe == null || oe.getName().equalsIgnoreCase(getName())) {
                OlapElement oeLevel =
                    getHierarchy().lookupChild(schemaReader, s, matchType);
                if (oeLevel != null) {
                    return oeLevel; // level match overrides hierarchy match
                }
            }
            return oe;
        } else {
            // New (SSAS-compatible) behavior. If there is no matching
            // hierarchy, find the first level with the given name.
            if (oe != null) {
                return oe;
            }
            final List<Hierarchy> hierarchyList =
                schemaReader.getDimensionHierarchies(this);
            for (Hierarchy hierarchy : hierarchyList) {
                oe = hierarchy.lookupChild(schemaReader, s, matchType);
                if (oe != null) {
                    return oe;
                }
            }
            return null;
        }
    }

    public boolean isHighCardinality() {
        return this.highCardinality;
    }

    private Hierarchy lookupHierarchy(Id.NameSegment s) {
        for (Hierarchy hierarchy : hierarchies) {
            if (Util.equalName(hierarchy.getName(), s.getName())) {
                return hierarchy;
            }
        }
        return null;
    }
}

// End DimensionBase.java
