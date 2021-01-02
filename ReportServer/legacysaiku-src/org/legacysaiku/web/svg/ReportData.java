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
 
 
package org.legacysaiku.web.svg;

import java.util.ArrayList;

import org.legacysaiku.olap.dto.resultset.AbstractBaseCell;
import org.legacysaiku.olap.dto.resultset.MemberCell;

public class ReportData {
	private AbstractBaseCell[][] rowHeader;
	private AbstractBaseCell[][] rowBody;

	public AbstractBaseCell[][] getRowHeader() {
		return rowHeader;
	}

	public void setRowHeader(AbstractBaseCell[][] rowHeader) {
		this.rowHeader = rowHeader;
	}

	public AbstractBaseCell[][] getRowBody() {
		return rowBody;
	}

	public void setRowBody(AbstractBaseCell[][] rowBody) {
		this.rowBody = rowBody;
	}

	class Section {
		private String des;
		private ArrayList<Section> child = new ArrayList<Section>();
		private Section parent;
		private AbstractBaseCell[][] data;
		private ArrayList<String> head;

		public ArrayList<String> getHead() {
			return head;
		}

		public void setHead(ArrayList<String> head) {
			this.head = head;
		}

		public String getDes() {
			return des;
		}

		public void setDes(String des) {
			this.des = des;
		}

		public ArrayList<Section> getChild() {
			return child;
		}

		public void setChild(ArrayList<Section> child) {
			this.child = child;
		}

		public Section getParent() {
			return parent;
		}

		public void setParent(Section parent) {
			this.parent = parent;
		}

		public AbstractBaseCell[][] getData() {
			return data;
		}

		public void setData(AbstractBaseCell[][] data) {
			this.data = data;
		}
	}


	public int dimTab(AbstractBaseCell[][] rowBody,
			AbstractBaseCell[][] rowHeader) {
		int dim=0;

		for (int j = 0; j < rowBody[0].length - 1; j++) {
			if (rowBody[0][j].getClass().equals(MemberCell.class)) {
				dim++;
			}
		}
		return dim;

	}

	public  ArrayList<Section> section(AbstractBaseCell[][] dataMatrix, int dimIndex, int dim, Section parent) {
		ArrayList<Section> sections = new ArrayList<Section>();
		Section section = null;

		for (int i = 0; i < dataMatrix.length; i++) {
			if (dataMatrix[i][dimIndex].getRawValue() != null) {
				section = new Section();

				int start = i; 
				i++;
				while (i < dataMatrix.length
						&& dataMatrix[i][dimIndex].getRawValue() == null) {
					i++;  
				}
				if(dim==1)i=dataMatrix.length;
				int row=i - start;
				if(dataMatrix[0][dim-1].getClass().equals(MemberCell.class) 
						&& dataMatrix[0][dim-1].getRawValue()==null &&
						dataMatrix[0][0].getParentDimension()==(dataMatrix[1][dim-1].getParentDimension())){
					row=row-1;
				}
				AbstractBaseCell[][] abc = new AbstractBaseCell[row][dataMatrix[start].length];
				int z=0;
				for (int j = start; j < i; j++) {

					for (int t = 0; t < dataMatrix[start].length; t++) {

						if(row!=i-start && 
								!dataMatrix[j][dim].getClass().equals(MemberCell.class) &&
								j==start)j++;
						abc[z][t] = dataMatrix[j][t];	
					}
					z++;
				}
				int temp=dim;
				if(dim>1){
					section.setDes(dataMatrix[start][dimIndex].getRawValue());
					temp=dim-1;}
				if (dimIndex == temp-1 ) {
					section.setData(abc);
					section.setHead(parent.getHead());
					section.setParent(parent);
				} else {
					section.setHead(parent.getHead());
					parent = section;
				}
				if (section.getData() == null)
					section.setChild(section(abc, dimIndex + 1, dim, parent));
				sections.add(section);
				i--;
			} else {
				// Scorre
			}
		}

		return sections;
	}




	public ArrayList<Section> section(AbstractBaseCell[][] dataMatrix,
			AbstractBaseCell[][] headMatrix, int rowIndex, int dim,
			Section parent) {
		ArrayList<Section> sections = new ArrayList<Section>();

		Section section = null;
		if (rowHeader.length == 1) {
			section = new Section();
			ArrayList<String> head = new ArrayList<String>();
			for (int j = dim-1; j < headMatrix[headMatrix.length - 1].length; j++) {
				head.add(headMatrix[headMatrix.length - 1][j].getRawValue());
			}
			section.setHead(head);
			section.setChild(section(dataMatrix, 0, dim, section));
			sections.add(section);
		}
		else {
			for (int i = 1; i < headMatrix[0].length; i++) {
				if(headMatrix[rowIndex][i]!=null)
					if (headMatrix[rowIndex][i].getRawValue() != "MeasuresLevel"
					&& headMatrix[rowIndex][i].getRawValue() != null ) {
						section = new Section();
						section.setDes(headMatrix[rowIndex][i].getRawValue());
						sections.add(section);

						int start = i;
						Boolean tot=false; 
						if(i<headMatrix[0].length-1 && headMatrix[rowIndex][i+1].getRawValue()==null){
							tot=true;
							while (i < headMatrix[rowIndex].length - 1 &&
									headMatrix[rowIndex][i+1].getRawValue()==null) {
								i++;
							}

						}
						else{
							while (i < headMatrix[rowIndex].length - 1
									&& headMatrix[rowIndex][i].getRawValue().equals(
											headMatrix[rowIndex][i + 1].getRawValue())) {
								i++;
							}
						}
						AbstractBaseCell[][] headm = new AbstractBaseCell[headMatrix.length-rowIndex-1][i - start + dim + 1];
						for (int j = 0; j < headMatrix.length-rowIndex-1; j++) {
							for (int t = start; t <= i; t++) {
								if (headMatrix[j][t] != null)
									headm[j][t - start + dim] = headMatrix[j+1][t];
							}
						}

						//body child
						AbstractBaseCell[][] datarow = new AbstractBaseCell[dataMatrix.length][i - start + dim + 1];
						for (int j = 0; j < dataMatrix.length; j++) {
							for (int t = 0; t < dim; t++) {
								datarow[j][t] = dataMatrix[j][t];

							}
							for (int t = start; t <= i; t++) {
								if (dataMatrix[j][t] != null)
									datarow[j][t - start + dim] = dataMatrix[j][t];
							}
						}
						ArrayList<String> head = new ArrayList<String>();

						head.add(rowHeader[rowHeader.length - 1][dim-1]
								.getRawValue());
						int k=0;
						for (int j = start; j <= i; j++) {
							if (j != 0)
								if(tot && k==0){
									head.add("Totale");
									k=1;
								}
								else
									head.add(headMatrix[headMatrix.length - 1][j]
											.getRawValue());

						}
						if (rowIndex == headMatrix.length - 2) {
							section.setParent(parent);
							section.setHead(head);
							section.setChild(section(datarow, 0, dim, section));
							parent = section;
						} else {
							section.setHead(head);
							parent = section;
							section.setChild(section(datarow, headm,
									rowIndex, dim, parent));
						}

					}
			}
		}
		return sections;
	}

}

