package com.example.carrental.Services;

import com.example.carrental.Model.Car;
import com.example.carrental.Model.Client;
import com.example.carrental.Model.Rent;
import com.example.carrental.Model.User;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.FileNotFoundException;
import java.time.LocalDate;

public class PdfService {
    public static Cell getHeaderTextCell(String textValue){
        return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }
    public static Cell getHeaderTextCellValue(String textValue){
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
    public static Cell getCompanyAndClientCell(String textValue){
        return new Cell().add(textValue).setFontSize(12f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
    public static Cell getCellDataLeft(String textValue,Boolean isBold){
        Cell cell = new Cell().add(textValue).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold ? cell.setBold() : cell;
    }

    public static void generateInvoice(Rent rent, Car car, Client client, User user){
        String path = String.format("invoice%d.pdf", rent.getId());

        try {
            PdfWriter pdfWriter = new PdfWriter(path);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            Document document = new Document(pdfDocument);

            float threeCol = 190f;
            float twoCol = 285f;
            float twoCol150 = twoCol + 150f;
            float twoColumnWidth[] = {twoCol150, twoCol};
            float threeColumnWidth[] = {threeCol, threeCol, threeCol};
            float fullWidth[] = {threeCol * 3};
            Paragraph oneSp = new Paragraph("\n");

            Table table = new Table(twoColumnWidth);
            table.addCell(new Cell().add("Invoice").setFontSize(20f).setBorder(Border.NO_BORDER).setBold());

            Table nestedTable = new Table(new float[]{twoCol / 2, twoCol / 2});
            nestedTable.addCell(PdfService.getHeaderTextCell("Invoice No."));
            nestedTable.addCell(PdfService.getHeaderTextCellValue("RO-" + rent.getId()));
            nestedTable.addCell(PdfService.getHeaderTextCell("Invoice Date"));
            nestedTable.addCell(PdfService.getHeaderTextCellValue(String.valueOf(LocalDate.now())));
            nestedTable.addCell(PdfService.getHeaderTextCell("CAEN:"));
            nestedTable.addCell(PdfService.getHeaderTextCellValue("7711" + rent.getId()));

            table.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));

            Border gb = new SolidBorder(Color.GRAY, 0.5f);
            Table divider = new Table(fullWidth);
            divider.setBorder(gb);

            document.add(table);
            document.add(oneSp);
            document.add(divider);

            document.add(oneSp);
            Table companyClienttable = new Table(twoColumnWidth);
            companyClienttable.addCell(PdfService.getCompanyAndClientCell("Rent Company"));
            companyClienttable.addCell(PdfService.getCompanyAndClientCell("Client"));
            document.add(companyClienttable.setMarginBottom(12f));

            Table companyClientValuesTable1 = new Table(twoColumnWidth);
            companyClientValuesTable1.addCell(PdfService.getCellDataLeft("Company", true));
            companyClientValuesTable1.addCell(PdfService.getCellDataLeft("Name", true));
            companyClientValuesTable1.addCell(PdfService.getCellDataLeft("Rent-Car", false));
            companyClientValuesTable1.addCell(PdfService.getCellDataLeft(client.getName(), false));
            document.add(companyClientValuesTable1);

            Table companyClientValuesTable2 = new Table(twoColumnWidth);
            companyClientValuesTable2.addCell(PdfService.getCellDataLeft("Employ Name", true));
            companyClientValuesTable2.addCell(PdfService.getCellDataLeft("Phone", true));
            companyClientValuesTable2.addCell(PdfService.getCellDataLeft(user.getName(), false));
            companyClientValuesTable2.addCell(PdfService.getCellDataLeft("0" + client.getPhone(), false));
            document.add(companyClientValuesTable2);

            float oneColumnTable[] = {twoCol150};

            Table oneColTable1 = new Table(oneColumnTable);
            oneColTable1.addCell(PdfService.getCellDataLeft("Address", true));
            oneColTable1.addCell(PdfService.getCellDataLeft("Romania, Baia Mare,\nstr. Victor Babes, nr 7A", false));
            oneColTable1.addCell(PdfService.getCellDataLeft("Email", true));
            oneColTable1.addCell(PdfService.getCellDataLeft("prorent9@gmail.com", false));
            document.add(oneColTable1.setMarginBottom(10f));

            Table tableDivider1 = new Table(fullWidth);
            Border dgb = new DashedBorder(Color.GRAY, 0.5f);
            document.add(tableDivider1.setBorder(dgb));
            document.add(oneSp);

            Paragraph itemsTextParagraph = new Paragraph("Items");
            document.add(itemsTextParagraph.setBold());

            Table threeColTable1 = new Table(threeColumnWidth);
            threeColTable1.setBackgroundColor(Color.BLACK, 0.7f);
            threeColTable1.addCell(new Cell().add("Car").setBold().setFontColor(Color.WHITE).setBorder(Border.NO_BORDER));
            threeColTable1.addCell(new Cell().add("Date").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            threeColTable1.addCell(new Cell().add("Price/Day(RON)").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);

            document.add(threeColTable1.setMarginBottom(20f));

            Table threeColTable2 = new Table(threeColumnWidth);

            threeColTable2.addCell(new Cell().add(car.getBrand() + " " + car.getModel() + "\n" + car.getRegistrationNumber()).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
            threeColTable2.addCell(new Cell().add(rent.getStartDateRent() + "\n" + rent.getEndDaterRent()).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            threeColTable2.addCell(new Cell().add(String.valueOf(car.getPricePerDay())).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);

            document.add(threeColTable2.setMarginBottom(20f));

            //linia de separare jumate
            float oneTwo[] = {threeCol + 125f, threeCol * 2};
            Table threeColTable3 = new Table(oneTwo);
            threeColTable3.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            threeColTable3.addCell(new Cell().add(tableDivider1).setBorder(Border.NO_BORDER));

            document.add(threeColTable3);

            Table threeColTable4 = new Table(threeColumnWidth);
            threeColTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER)).setMarginLeft(10f);
            threeColTable4.addCell(new Cell().add("Total").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            threeColTable4.addCell(new Cell().add(String.valueOf(rent.getTotalPrice())).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);

            document.add(threeColTable4);
            document.add(tableDivider1);
            document.add(new Paragraph("\n"));
            document.add(divider.setBorder(new SolidBorder(Color.GRAY, 1)).setMarginBottom(15f));

            document.close();

            AlertService.confirmCreatePdfAskSendMail("PDF created successfully!", "Do you want to send the invoice by email??", rent.getId(), client.getEmail());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
