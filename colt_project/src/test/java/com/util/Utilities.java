package com.util;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.testng.Assert;

import com.util.DriverTestCase.BuildingType;
import com.util.DriverTestCase.CurrencyType;

public class Utilities {

	// Get absolute path
	public static String getPath() {
		String path = "";
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		path = absolutePathOfFirstFile.replaceAll("\\\\+", "/");
		return path;
	}

	// Creating file name
	public static String getFileName(String file) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		Calendar cal = Calendar.getInstance();
		String fileName = file + dateFormat.format(cal.getTime());
		return fileName;
	}

	// Get absolute path
	public static String getPathUpload() {
		String path = "";
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		path = absolutePathOfFirstFile.replaceAll("/", "//");
		return path;
	}

	// Get random integer
	public static int getRandomInteger(int aStart, int aEnd) {
		Random aRandom = new Random();
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		// get the range, casting to long to avoid overflow problems
		long range = (long) aEnd - (long) aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * aRandom.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		return randomNumber;
	}

	// Get random string
	public static String randomString(int len) {
		String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public static String currencyConvertor(String dbPrice, String dbCurrency, String expectedCurrency) {
		  String finalPrice = null;
		  if (!(dbPrice.equals("NA") || dbPrice == null)) {

		   Double price = Double.parseDouble(dbPrice);
		   Double tmp = 0.0;

		   NumberFormat format = null;
		   CurrencyType expectedCurrecyType = CurrencyType.valueOf(expectedCurrency);

		   CurrencyType dbCurrecyType = CurrencyType.valueOf(dbCurrency);

		   switch (expectedCurrecyType) {
		   case EUR:
		    format = NumberFormat.getCurrencyInstance(Locale.GERMANY);
		    switch (dbCurrecyType) {
		    case EUR:
		     tmp = price;
		     break;
		    case GBP:
		     tmp = price * 1.13216;
		     break;
		    case USD:
		     tmp = price * 0.810573192;
		     break;
		    case SEK:
		     tmp = price * 0.098477468;
		     break;
		    case SDR:
		     tmp = price * 1.187471219;
		     break;
		    case SDG:
		     tmp = price * 0.61632262;
		     break;
		    case CHF:
		     tmp = price * 0.8561791985;
		     break;
		    case DKK:
		     tmp = price * 0.134250026;
		     break;
		    case INR:
		     tmp = price * 0.012461019;
		     break;
		    case NOK:
		     tmp = price * 0.104382813;
		     break;
		    case JPY:
		     tmp = price * 0.007642761;
		     break;
		    case PLN:
		     tmp = price * 0.240162927;
		     break;
		    case RON:
		     tmp = price * 0.214520126;
		     break;
		    case HKD:
		     tmp = price * 0.103383222;
		     break;
		    case AUD:
		     tmp = price * 0.629649404;
		     break;
		    case CAD:
		     tmp = price * 0.627131225;
		     break;
		    case CNY:
		     tmp = price * 0.128193486;
		     break;
		    case KRW:
		     tmp = price * 0.000755529;
		     break;
		    case CZK:
		     tmp = price * 0.039324629;
		     break;
		    default:
		     Assert.fail("Currency type fetched from DB is not found");
		    }
		    break;
		   case GBP:
		    format = NumberFormat.getCurrencyInstance(Locale.UK);
		    switch (dbCurrecyType) {
		    case GBP:
		     tmp = price;
		     break;
		    case USD:
		     tmp = price * 0.631176795;
		     break;
		    case SEK:
		     tmp = price * 0.097041031;
		     break;
		    case CHF:
		     tmp = price * 0.682107097;
		     break;
		    case DKK:
		     tmp = price * 0.112898946;
		     break;
		    case EUR:
		     tmp = price * 0.841993000;
		     break;
		    default:
		     Assert.fail("Currency type fetched from DB is not found");
		    }
		    break;
		   case USD:
		    format = NumberFormat.getCurrencyInstance(Locale.US);
		    switch (dbCurrecyType) {
		    case USD:
		     tmp = price;
		     break;
		    case GBP:
		     tmp = price * 1.58434215;
		     break;
		    case SEK:
		     tmp = price * 0.153746195;
		     break;
		    case CHF:
		     tmp = price * 1.080691024;
		     break;
		    case DKK:
		     tmp = price * 0.178870559;
		     break;
		    case EUR:
		     tmp = price * 1.334005000;
		     break;
		    default:
		     Assert.fail("Currency type fetched from DB is not found");
		    }
		    break;
		   default:
		    Assert.fail("Currency type not found");
		   }
		   BigDecimal payment = new BigDecimal(tmp);
		   BigDecimal displayVal = payment.setScale(1, RoundingMode.DOWN);
		   finalPrice = format.format(displayVal);
		   finalPrice = finalPrice.substring(0, finalPrice.length() - 4);
		   System.out.println(finalPrice);

		   if (expectedCurrency.equals("EUR"))
		    finalPrice = finalPrice.replace("€", "");
		   if (expectedCurrency.equals("USD"))
		    finalPrice = finalPrice.replace("$", "");
		   if (expectedCurrency.equals("GBP"))
		    finalPrice = finalPrice.replace("£", "");
		  }
		  return finalPrice;
		 }

	public static String nrcPriceAsPerContractTerm(String term, String nrc) {
		BigDecimal ONE_HUNDRED = new BigDecimal(100);
		BigDecimal _nrc = new BigDecimal(nrc);
		BigDecimal finalNrc = new BigDecimal(nrc);
		BigDecimal tmp = new BigDecimal("0");
		Double _term = Double.parseDouble(term);

		if (_term > 23 && _term < 36) {
			tmp = _nrc.multiply(new BigDecimal(25).divide(ONE_HUNDRED));
			finalNrc = _nrc.subtract(tmp);
		}

		if (_term > 35 && _term < 48) {
			tmp = _nrc.multiply(new BigDecimal(50).divide(ONE_HUNDRED));
			finalNrc = _nrc.subtract(tmp);
		}
		return finalNrc.toString();
	}

	public static String mrcPriceAsPerContractTerm(String term, String mrc) {
		BigDecimal ONE_HUNDRED = new BigDecimal(100);
		BigDecimal _mrc = new BigDecimal(mrc);
		BigDecimal finalMrc = new BigDecimal(mrc);
		BigDecimal tmp = new BigDecimal("0");
		Double _term = Double.parseDouble(term);

		if (_term > 23 && _term < 36) {
			tmp = _mrc.multiply(new BigDecimal(5).divide(ONE_HUNDRED));
			finalMrc = _mrc.subtract(tmp);
		}

		if (_term > 35 && _term < 48) {
			tmp = _mrc.multiply(new BigDecimal(10).divide(ONE_HUNDRED));
			finalMrc = _mrc.subtract(tmp);
		}

		return finalMrc.toString();

	}
	
	public static String getCurrentDateAndTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
