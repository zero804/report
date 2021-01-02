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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities.format.enums.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.IllegalArgumentException;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.client.reportengines.table.dto.CurrencyTypeDto;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.enums.CurrencyType;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.enums.dtogen.CurrencyType2DtoGenerator;

/**
 * Poso2DtoGenerator for CurrencyType
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class CurrencyType2DtoGenerator implements Poso2DtoGenerator<CurrencyType,CurrencyTypeDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public CurrencyType2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public CurrencyTypeDto instantiateDto(CurrencyType poso)  {
		/* Simply return the first enum! */
		CurrencyTypeDto dto = CurrencyTypeDto.EURO;
		return dto;
	}

	public CurrencyTypeDto createDto(CurrencyType poso, DtoView here, DtoView referenced)  {
		switch(poso){
			case EURO:
				return CurrencyTypeDto.EURO;
			case DOLLAR:
				return CurrencyTypeDto.DOLLAR;
			case BRITTISH_POUND:
				return CurrencyTypeDto.BRITTISH_POUND;
			case AED:
				return CurrencyTypeDto.AED;
			case AFN:
				return CurrencyTypeDto.AFN;
			case ALL:
				return CurrencyTypeDto.ALL;
			case AMD:
				return CurrencyTypeDto.AMD;
			case ANG:
				return CurrencyTypeDto.ANG;
			case AOA:
				return CurrencyTypeDto.AOA;
			case ARS:
				return CurrencyTypeDto.ARS;
			case AUD:
				return CurrencyTypeDto.AUD;
			case AWG:
				return CurrencyTypeDto.AWG;
			case AZN:
				return CurrencyTypeDto.AZN;
			case BAM:
				return CurrencyTypeDto.BAM;
			case BBD:
				return CurrencyTypeDto.BBD;
			case BDT:
				return CurrencyTypeDto.BDT;
			case BGN:
				return CurrencyTypeDto.BGN;
			case BHD:
				return CurrencyTypeDto.BHD;
			case BIF:
				return CurrencyTypeDto.BIF;
			case BMD:
				return CurrencyTypeDto.BMD;
			case BND:
				return CurrencyTypeDto.BND;
			case BOB:
				return CurrencyTypeDto.BOB;
			case BRL:
				return CurrencyTypeDto.BRL;
			case BSD:
				return CurrencyTypeDto.BSD;
			case BTN:
				return CurrencyTypeDto.BTN;
			case BWP:
				return CurrencyTypeDto.BWP;
			case BYR:
				return CurrencyTypeDto.BYR;
			case BZD:
				return CurrencyTypeDto.BZD;
			case CAD:
				return CurrencyTypeDto.CAD;
			case CDF:
				return CurrencyTypeDto.CDF;
			case CHF:
				return CurrencyTypeDto.CHF;
			case CLP:
				return CurrencyTypeDto.CLP;
			case CNY:
				return CurrencyTypeDto.CNY;
			case COP:
				return CurrencyTypeDto.COP;
			case CRC:
				return CurrencyTypeDto.CRC;
			case CUC:
				return CurrencyTypeDto.CUC;
			case CUP:
				return CurrencyTypeDto.CUP;
			case CVE:
				return CurrencyTypeDto.CVE;
			case CZK:
				return CurrencyTypeDto.CZK;
			case DJF:
				return CurrencyTypeDto.DJF;
			case DKK:
				return CurrencyTypeDto.DKK;
			case DOP:
				return CurrencyTypeDto.DOP;
			case DZD:
				return CurrencyTypeDto.DZD;
			case EGP:
				return CurrencyTypeDto.EGP;
			case ERN:
				return CurrencyTypeDto.ERN;
			case ETB:
				return CurrencyTypeDto.ETB;
			case EUR:
				return CurrencyTypeDto.EUR;
			case FJD:
				return CurrencyTypeDto.FJD;
			case FKP:
				return CurrencyTypeDto.FKP;
			case GBP:
				return CurrencyTypeDto.GBP;
			case GEL:
				return CurrencyTypeDto.GEL;
			case GGP:
				return CurrencyTypeDto.GGP;
			case GHS:
				return CurrencyTypeDto.GHS;
			case GIP:
				return CurrencyTypeDto.GIP;
			case GMD:
				return CurrencyTypeDto.GMD;
			case GNF:
				return CurrencyTypeDto.GNF;
			case GTQ:
				return CurrencyTypeDto.GTQ;
			case GYD:
				return CurrencyTypeDto.GYD;
			case HKD:
				return CurrencyTypeDto.HKD;
			case HNL:
				return CurrencyTypeDto.HNL;
			case HRK:
				return CurrencyTypeDto.HRK;
			case HTG:
				return CurrencyTypeDto.HTG;
			case HUF:
				return CurrencyTypeDto.HUF;
			case IDR:
				return CurrencyTypeDto.IDR;
			case ILS:
				return CurrencyTypeDto.ILS;
			case IMP:
				return CurrencyTypeDto.IMP;
			case INR:
				return CurrencyTypeDto.INR;
			case IQD:
				return CurrencyTypeDto.IQD;
			case IRR:
				return CurrencyTypeDto.IRR;
			case ISK:
				return CurrencyTypeDto.ISK;
			case JEP:
				return CurrencyTypeDto.JEP;
			case JMD:
				return CurrencyTypeDto.JMD;
			case JOD:
				return CurrencyTypeDto.JOD;
			case JPY:
				return CurrencyTypeDto.JPY;
			case KES:
				return CurrencyTypeDto.KES;
			case KGS:
				return CurrencyTypeDto.KGS;
			case KHR:
				return CurrencyTypeDto.KHR;
			case KMF:
				return CurrencyTypeDto.KMF;
			case KPW:
				return CurrencyTypeDto.KPW;
			case KRW:
				return CurrencyTypeDto.KRW;
			case KWD:
				return CurrencyTypeDto.KWD;
			case KYD:
				return CurrencyTypeDto.KYD;
			case KZT:
				return CurrencyTypeDto.KZT;
			case LAK:
				return CurrencyTypeDto.LAK;
			case LBP:
				return CurrencyTypeDto.LBP;
			case LKR:
				return CurrencyTypeDto.LKR;
			case LRD:
				return CurrencyTypeDto.LRD;
			case LSL:
				return CurrencyTypeDto.LSL;
			case LYD:
				return CurrencyTypeDto.LYD;
			case MAD:
				return CurrencyTypeDto.MAD;
			case MDL:
				return CurrencyTypeDto.MDL;
			case MGA:
				return CurrencyTypeDto.MGA;
			case MKD:
				return CurrencyTypeDto.MKD;
			case MMK:
				return CurrencyTypeDto.MMK;
			case MNT:
				return CurrencyTypeDto.MNT;
			case MOP:
				return CurrencyTypeDto.MOP;
			case MRU:
				return CurrencyTypeDto.MRU;
			case MUR:
				return CurrencyTypeDto.MUR;
			case MVR:
				return CurrencyTypeDto.MVR;
			case MWK:
				return CurrencyTypeDto.MWK;
			case MXN:
				return CurrencyTypeDto.MXN;
			case MYR:
				return CurrencyTypeDto.MYR;
			case MZN:
				return CurrencyTypeDto.MZN;
			case NAD:
				return CurrencyTypeDto.NAD;
			case NGN:
				return CurrencyTypeDto.NGN;
			case NIO:
				return CurrencyTypeDto.NIO;
			case NOK:
				return CurrencyTypeDto.NOK;
			case NPR:
				return CurrencyTypeDto.NPR;
			case NZD:
				return CurrencyTypeDto.NZD;
			case OMR:
				return CurrencyTypeDto.OMR;
			case PAB:
				return CurrencyTypeDto.PAB;
			case PEN:
				return CurrencyTypeDto.PEN;
			case PGK:
				return CurrencyTypeDto.PGK;
			case PHP:
				return CurrencyTypeDto.PHP;
			case PKR:
				return CurrencyTypeDto.PKR;
			case PLN:
				return CurrencyTypeDto.PLN;
			case PYG:
				return CurrencyTypeDto.PYG;
			case QAR:
				return CurrencyTypeDto.QAR;
			case RON:
				return CurrencyTypeDto.RON;
			case RSD:
				return CurrencyTypeDto.RSD;
			case RUB:
				return CurrencyTypeDto.RUB;
			case RWF:
				return CurrencyTypeDto.RWF;
			case SAR:
				return CurrencyTypeDto.SAR;
			case SBD:
				return CurrencyTypeDto.SBD;
			case SCR:
				return CurrencyTypeDto.SCR;
			case SDG:
				return CurrencyTypeDto.SDG;
			case SEK:
				return CurrencyTypeDto.SEK;
			case SGD:
				return CurrencyTypeDto.SGD;
			case SHP:
				return CurrencyTypeDto.SHP;
			case SLL:
				return CurrencyTypeDto.SLL;
			case SOS:
				return CurrencyTypeDto.SOS;
			case SRD:
				return CurrencyTypeDto.SRD;
			case SSP:
				return CurrencyTypeDto.SSP;
			case STN:
				return CurrencyTypeDto.STN;
			case SVC:
				return CurrencyTypeDto.SVC;
			case SYP:
				return CurrencyTypeDto.SYP;
			case SZL:
				return CurrencyTypeDto.SZL;
			case THB:
				return CurrencyTypeDto.THB;
			case TJS:
				return CurrencyTypeDto.TJS;
			case TMT:
				return CurrencyTypeDto.TMT;
			case TND:
				return CurrencyTypeDto.TND;
			case TOP:
				return CurrencyTypeDto.TOP;
			case TRY:
				return CurrencyTypeDto.TRY;
			case TTD:
				return CurrencyTypeDto.TTD;
			case TVD:
				return CurrencyTypeDto.TVD;
			case TWD:
				return CurrencyTypeDto.TWD;
			case TZS:
				return CurrencyTypeDto.TZS;
			case UAH:
				return CurrencyTypeDto.UAH;
			case UGX:
				return CurrencyTypeDto.UGX;
			case USD:
				return CurrencyTypeDto.USD;
			case UYU:
				return CurrencyTypeDto.UYU;
			case UZS:
				return CurrencyTypeDto.UZS;
			case VEF:
				return CurrencyTypeDto.VEF;
			case VND:
				return CurrencyTypeDto.VND;
			case VUV:
				return CurrencyTypeDto.VUV;
			case WST:
				return CurrencyTypeDto.WST;
			case XAF:
				return CurrencyTypeDto.XAF;
			case XCD:
				return CurrencyTypeDto.XCD;
			case XOF:
				return CurrencyTypeDto.XOF;
			case XPF:
				return CurrencyTypeDto.XPF;
			case YER:
				return CurrencyTypeDto.YER;
			case ZAR:
				return CurrencyTypeDto.ZAR;
			case ZMW:
				return CurrencyTypeDto.ZMW;
			case ZWL:
				return CurrencyTypeDto.ZWL;
		}
		throw new IllegalArgumentException("unknown enum type for: " + poso);
	}


}
