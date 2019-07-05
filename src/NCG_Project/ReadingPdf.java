package NCG_Project;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.text.NumberFormat;

public class ReadingPdf {
	private String FileDir;
	private int InvoiceNum;
	private String InvoiceDate;
	private int CustomerPO;
	private String Address = "";
	private double Amount;
	private int PageNo;
	private int invoiceCount;
	private String vendorName;

	public ReadingPdf(String FileDir, int PageNo,String vendorName) {
		this.FileDir = FileDir;
		this.PageNo = PageNo;
		this.invoiceCount = 0;
		this.vendorName=vendorName;
	}

	private void ReadPDf() throws Exception {
		try (PDDocument document = PDDocument.load(new File(FileDir))) {

			if (!document.isEncrypted()) {

				PDFTextStripper stripper = new PDFTextStripper();
				
				if (PageNo < document.getNumberOfPages()) {

					
					stripper.setStartPage(PageNo);

					stripper.setEndPage(PageNo);
					String pages = stripper.getText(document);
					
					if (pages.contains("Invoice No") && pages.contains("Net Order Total"))

					{
						

						String[] lines = pages.split("\r\n|\r|\n");
						for (int i = 0; i < lines.length; i++) {
							switch (lines[i]) {
							case "Invoice No": {
								InvoiceNum = Integer.parseInt(lines[++i].trim());
								break;
							}
							case "Invoice Date": {
								InvoiceDate = lines[++i].trim();
								break;
							}
							case "Customer P.O.": {
								String tempp=lines[++i].replaceAll("[-#PO]", "");
								CustomerPO = ((Number) NumberFormat.getInstance().parse(tempp)).intValue();
								
								break;
							}
							case "Remit To": {
								while (true) {
									i++;
									if (lines[i].equals("Payment Terms")) {
										break;
									} else {
										Address += lines[i];
										Address += " ";
									}
								}
								break;
							}
							case "Total Invoice": {
								while (true) {
									if (lines[i].contains("Invoices not paid")) {
										i--;
										break;
									}

									else {
										i++;
									}
								}
								String temp=lines[i].replaceAll("[$,]", "");
								//System.out.println("temp"+temp);
								Amount=Double.valueOf(temp);
								//System.out.println(Amount);
								break;
							}

							}
						}
						invoiceCount++;
						//System.out.println("invoice added");
						InserttoDatabase inserttoDatabase = new InserttoDatabase(InvoiceNum, InvoiceDate, CustomerPO,
								Address, Amount,vendorName);
						inserttoDatabase.insert();
					}
					PageNo++;
				}
				if (invoiceCount == 0) {
					System.out.println("There is no invoice details present in this pdf");

				} 

			} else {
				System.out.println("The PDF file is Encrypted, Data can't be extracted");
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println(
					"------------------------------------------------program ended------------------------------------------------");
			System.exit(0);
		}

	}

	public void GetData() throws Exception {

		ReadPDf();

	}

}
