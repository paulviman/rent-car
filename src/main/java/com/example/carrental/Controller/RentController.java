package com.example.carrental.Controller;

import com.example.carrental.Main;
import com.example.carrental.Model.Car;
import com.example.carrental.Model.Client;
import com.example.carrental.Model.Rent;
import com.example.carrental.Services.PdfService;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import com.example.carrental.Services.DatabaseService;
import javafx.scene.layout.Pane;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.pdf.*;

public class RentController {

    @javafx.fxml.FXML
    private Label rentDate;
    @javafx.fxml.FXML
    private Label rentAddress;
    @javafx.fxml.FXML
    private Label rentClient;
    @javafx.fxml.FXML
    private Label rentCar;
    @javafx.fxml.FXML
    private Label rentPrice;

    DatabaseService databaseService = new DatabaseService();
    @javafx.fxml.FXML
    private Pane frontColorPane = new Pane();
    @javafx.fxml.FXML
    private Pane backColorPane = new Pane();
    @javafx.fxml.FXML
    private Label returnDate;
    @javafx.fxml.FXML
    private Label returnAddress;
    @javafx.fxml.FXML
    private Label rentClientPhone;
    @javafx.fxml.FXML
    private Label rentCarRegNumb;
    @javafx.fxml.FXML
    private Label idRent;
    @javafx.fxml.FXML
    private Button btnGenerateInvoicePdf;
    @javafx.fxml.FXML
    private Button btnEditRent;

    Rent rent;
    Client client;
    Car car;

    public ArrayList<Rent> populateListRentFromDB(ArrayList<Car> cars, ArrayList<Client> clients) {
        return databaseService.getAllRent(cars, clients);
    }


    public Node createCardNode(Rent rent) {
        //obtin datele dspre clinet si car facand un query in db
        this.rent = rent;
        this.client = databaseService.getClient(rent.getClientId());
        this.car = databaseService.getCar(rent.getCarId());
        LocalDate currentDate = LocalDate.now();
        //calculez cate zile este inchiriata masina
        //long days = ChronoUnit.DAYS.between(rent.getStartDateRent().toInstant(), rent.getEndDaterRent().toInstant());
        //calculez pretul total pe zile
        //long total_price = days * car.getPricePerDay();

        try {
            // încărcarea fișierului FXML pentru cardul de rent
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("rent.fxml"));
            Parent root = loader.load();

            // obținerea controllerului pentru fișierul FXML
            RentController controller = loader.getController();

            controller.rent = rent;
            controller.client = databaseService.getClient(rent.getClientId());
            controller.car = databaseService.getCar(rent.getCarId());

            controller.idRent.setText(String.valueOf(rent.getId()));

            controller.rentDate.setText(String.valueOf(rent.getStartDateRent()));
            controller.returnDate.setText(String.valueOf(rent.getEndDaterRent()));

            controller.rentAddress.setText(rent.getPickUpAddress());
            controller.returnAddress.setText(rent.getReturnAddress());

            controller.rentClient.setText(client.getName());
            controller.rentClientPhone.setText("0" + client.getPhone());

            controller.rentCar.setText(car.getBrand() + " " + car.getModel());
            controller.rentCarRegNumb.setText(car.getRegistrationNumber());

            //controller.rentPrice.setText(String.valueOf(total_price));
            controller.rentPrice.setText(String.valueOf(rent.getTotalPrice()));
            if (!rent.isAvailable()) {
                controller.frontColorPane.setStyle("-fx-background-color: #FFEE58;");
                controller.backColorPane.setStyle("-fx-background-color: #FFEE58;");
                controller.btnGenerateInvoicePdf.setDisable(true);
                controller.btnEditRent.setDisable(true);

            }
            if (rent.getEndDaterRent().isBefore(currentDate)) {
                controller.frontColorPane.setStyle("-fx-background-color: #EF5350;");
                controller.backColorPane.setStyle("-fx-background-color: #EF5350;");
                controller.btnEditRent.setDisable(true);
            }
//            controller.frontColorPane.setStyle("-fx-background-color:  80CBC4;");
//            controller.backColorPane.setStyle("-fx-background-color:  #80CBC4;");


            return root;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void actionBtnGenerateInvoicePdf(javafx.event.ActionEvent actionEvent) {
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
            nestedTable.addCell(PdfService.getHeaderTextCellValue("12"));
            nestedTable.addCell(PdfService.getHeaderTextCell("Invoice Date"));
            nestedTable.addCell(PdfService.getHeaderTextCellValue("12.02.2023"));

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
            companyClientValuesTable2.addCell(PdfService.getCellDataLeft("Angajatul lunii", false));
            companyClientValuesTable2.addCell(PdfService.getCellDataLeft("0" + client.getPhone(), false));
            document.add(companyClientValuesTable2);

            float oneColumnTable[] = {twoCol150};

            Table oneColTable1 = new Table(oneColumnTable);
            oneColTable1.addCell(PdfService.getCellDataLeft("Address", true));
            oneColTable1.addCell(PdfService.getCellDataLeft("Romania, Baia Mare,\nstr. Victor Babes, nr 7A", false));
            oneColTable1.addCell(PdfService.getCellDataLeft("Email", true));
            oneColTable1.addCell(PdfService.getCellDataLeft("rent-car@gmail.com", false));
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

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void actionBtnEditRent(javafx.event.ActionEvent actionEvent) {
        btnGenerateInvoicePdf.setDisable(true);
        btnEditRent.setDisable(true);
        this.frontColorPane.setStyle("-fx-background-color: #FFEE58;");
        this.backColorPane.setStyle("-fx-background-color: #FFEE58;");
        databaseService.setRentAvailability(rent.getId(),false);
    }
}
