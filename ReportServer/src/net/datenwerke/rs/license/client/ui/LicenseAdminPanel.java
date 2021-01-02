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
 
 
package net.datenwerke.rs.license.client.ui;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormSubmissionListener;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCSpace;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCStaticHtml;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCStaticLabel;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCTextArea;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.dummy.Separator;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.dummy.StaticLabel;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.core.client.i18tools.FormatUiHelper;
import net.datenwerke.rs.enterprise.client.EnterpriseUiService;
import net.datenwerke.rs.license.client.LicenseDao;
import net.datenwerke.rs.license.client.dto.LicenseInformationDto;
import net.datenwerke.rs.license.client.locale.LicenseMessages;

/**
 * 
 *
 */
@Singleton
public class LicenseAdminPanel extends DwContentPanel {

	private final LicenseDao licenseDao;
	private final FormatUiHelper formatUiHelper;
	private final EnterpriseUiService enterpriseService;

	
	private VerticalLayoutContainer wrapper;

	@Inject
	public LicenseAdminPanel(
		LicenseDao licenseDao,
		FormatUiHelper formatUiHelper,
		EnterpriseUiService enterpriseService
		){
		
		this.licenseDao = licenseDao;
		this.formatUiHelper = formatUiHelper;
		this.enterpriseService = enterpriseService;
		
		/* initialize ui */
		initializeUI();
	}

	private void initializeUI() {
		setHeading(LicenseMessages.INSTANCE.dialogTitle());
		addStyleName("rs-license");
		
		wrapper = new VerticalLayoutContainer();
		wrapper.setScrollMode(ScrollMode.AUTOY);
		add(wrapper);
		
		updateView();
	}
	

	protected void updateView() {
		mask(BaseMessages.INSTANCE.loadingMsg());
		
		wrapper.clear();
		
		licenseDao.loadLicenseInformation(new RsAsyncCallback<LicenseInformationDto>(){
			@Override
			public void onSuccess(LicenseInformationDto result) {
				super.onSuccess(result);
				init(result);
				unmask();
			}
			@Override
			public void onFailure(Throwable caught) {
				super.onFailure(caught);
				unmask();
			}
		});
	}

	protected void init(final LicenseInformationDto result) {
		final SimpleForm form = SimpleForm.getNewInstance();
		form.setHeading(LicenseMessages.INSTANCE.informationPanelHeader());
		form.setLabelAlign(LabelAlign.LEFT);
		
		form.setLabelWidth(150);
		
		/* version */
		form.addField(StaticLabel.class, LicenseMessages.INSTANCE.versionLabel(), new SFFCStaticLabel() {
			@Override
			public String getLabel() {
				return result.getRsVersion();
			}
		});
		
		/* server id */
		form.addField(StaticLabel.class, LicenseMessages.INSTANCE.serverIdLabel(), new SFFCStaticLabel() {
			@Override
			public String getLabel() {
				return result.getServerId();
			}
		});
		
		/* installation date */
		form.addField(StaticLabel.class, LicenseMessages.INSTANCE.installationDateLabel(), new SFFCStaticLabel() {
			@Override
			public String getLabel() {
				if(null != result.getInstallationDate())
					return formatUiHelper.getLongDateFormat().format(result.getInstallationDate());
				return BaseMessages.INSTANCE.error();
			}
		});
		
		
		
		form.addField(Separator.class, new SFFCSpace());
		
		/* name */
		if(null != result.getName()){
			form.addField(StaticLabel.class, LicenseMessages.INSTANCE.licenseeLabel(), new SFFCStaticLabel() {
				@Override
				public String getLabel() {
					return result.getName();
				}
			});
		}
		
		/* license */
		form.addField(StaticLabel.class, LicenseMessages.INSTANCE.currentLicenseLabel(), new SFFCStaticHtml() {
			@Override
			public SafeHtml getLabel() {
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendEscaped(result.getLicenseType());
				if(enterpriseService.isCommunity())
					sb.appendHtmlConstant(" (<a target='_blank' href='https://reportserver.net/en/agpl-license/'>AGPL</a>)");
				
				return sb.toSafeHtml();
			}
		});
		
		/* expiration date */
		form.addField(StaticLabel.class, LicenseMessages.INSTANCE.licenseExpirationDate(), new SFFCStaticLabel() {
			@Override
			public String getLabel() {
				if(null != result.getExpirationDate())
					return formatUiHelper.getLongDateFormat().format(result.getExpirationDate());
				return LicenseMessages.INSTANCE.noExpirationDateMsg();
			}
		});
		
		/* upgrades until date */
		if(null != result.getUpgradesUntil()){
			form.addField(StaticLabel.class, LicenseMessages.INSTANCE.upgradesAvailableUntilLabel(), new SFFCStaticLabel() {
				@Override
				public String getLabel() {
					return formatUiHelper.getLongDateFormat().format(result.getUpgradesUntil());
				}
			});
		}
		
		form.addField(Separator.class, new SFFCSpace());
		
		/* Java version */
		if(null != result.getJavaVersion()){
			form.addField(StaticLabel.class, LicenseMessages.INSTANCE.javaVersionLabel(), new SFFCStaticLabel() {
				@Override
				public String getLabel() {
					return result.getJavaVersion();
				}
			});
		}
		
		/* Vm Arguments */
		if(null != result.getVmArguments()){
			form.addField(StaticLabel.class, "VM Args", new SFFCStaticLabel() {
				@Override
				public String getLabel() {
					return result.getVmArguments();
				}
			});
		}

		/* Application server */
		if(null != result.getApplicationServer()){
			form.addField(StaticLabel.class, LicenseMessages.INSTANCE.applicationServerLabel(), new SFFCStaticLabel() {
				@Override
				public String getLabel() {
					return result.getApplicationServer();
				}
			});
		}
		
		/* Operation system */
		if(null != result.getOsVersion()){
			form.addField(StaticLabel.class, LicenseMessages.INSTANCE.operationSystemLabel(), new SFFCStaticLabel() {
				@Override
				public String getLabel() {
					return result.getOsVersion();
				}
			});
		}
		
		form.addField(Separator.class, new SFFCSpace());
		
		/* Browser name */
		if(null != result.getBrowserName()){
			form.addField(StaticLabel.class, LicenseMessages.INSTANCE.browserNameLabel(), new SFFCStaticLabel() {
				@Override
				public String getLabel() {
					return result.getBrowserName();
				}
			});
		}

		/* Browser version */
		if(null != result.getBrowserVersion()){
			form.addField(StaticLabel.class, LicenseMessages.INSTANCE.browserVersionLabel(), new SFFCStaticLabel() {
				@Override
				public String getLabel() {
					return result.getBrowserVersion();
				}
			});
		}
		
		/* purchase info for community */
		if(enterpriseService.isCommunity() || enterpriseService.isEvaluation()){
			form.addField(StaticLabel.class, LicenseMessages.INSTANCE.purchaseEnterpriseLabel(), new SFFCStaticHtml() {
				@Override
				public SafeHtml getLabel() {
					SafeHtmlBuilder sb = new SafeHtmlBuilder();
					sb.appendHtmlConstant("<a target='_blank' href='https://reportserver.net/get-enterprise/'>")
					  .appendEscaped(LicenseMessages.INSTANCE.purchaseEnterpriseText())
					  .appendHtmlConstant("</a>");
					return sb.toSafeHtml();
				}
			});
		} 
		
		if(null != result.getAdditionalLicenseProperties()){
			form.addField(Separator.class, new SFFCSpace());
			for(final String key : result.getAdditionalLicenseProperties().keySet()){
				form.addField(StaticLabel.class, key, new SFFCStaticLabel() {
					@Override
					public String getLabel() {
						return result.getAdditionalLicenseProperties().get(key);
					}
				});
			}
		}
		
		if(!enterpriseService.isEnterpriseJarAvailable())
			form.getButtonBar().clear();
		else {
			form.addField(Separator.class, new SFFCSpace());
			
			form.addField(Separator.class);
			
			form.addField(Separator.class, new SFFCSpace());
			
			form.setLabelAlign(LabelAlign.TOP);
			form.setLabelWidth(500);
			final String licenseField = form.addField(String.class, LicenseMessages.INSTANCE.updateLicenseInfoFieldLabel(), new SFFCTextArea() {
				@Override
				public int getWidth() {
					return 1;
				}
				
				@Override
				public int getHeight() {
					return 300;
				}
			});
			
			form.getSubmitButton().setText(LicenseMessages.INSTANCE.updateLicenseInfoBtnLabel());
			
			form.addSubmissionListener(new SimpleFormSubmissionListener() {
				@Override
				public void formSubmitted(SimpleForm simpleForm) {
					String license = (String) form.getValue(licenseField);
					mask(BaseMessages.INSTANCE.loadingMsg());
					
					licenseDao.updateLicense(license, new RsAsyncCallback<Void>(){
						@Override
						public void onSuccess(Void result) {
							unmask();
							updateView();
						}
						
						@Override
						public void onFailure(Throwable caught) {
							super.onFailure(caught);
							unmask();
						}
					});
				}
			});
		}
		
		
		form.loadFields();

		
		
		wrapper.add(form, new VerticalLayoutData(1,-1, new Margins(10)));
		
		Scheduler.get().scheduleDeferred(forceLayoutCommand);
	}

	public void notifyOfSelection() {
	}


}
