package com.bo.hospital.utils;

import com.bo.hospital.pojo.Orders;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PdfUtil {

    public static void ExportPdf(HttpServletRequest request, HttpServletResponse response, Orders order) throws Exception {
        // Tell the browser which software can open this file
        response.setHeader("content-Type", "application/pdf");
        // Default download file name
       // response.setHeader("Content-Disposition", "attachment;filename=XXX.pdf");
        // Set CJK font
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
        // Create a document
        Document document = new Document(PageSize.A4);
        // Create the first paragraph
        Paragraph titleParagraph = new Paragraph();
        // Set CJK font
        titleParagraph.setFont(new Font(bfChinese, 20, Font.NORMAL));
        // Center the title
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        titleParagraph.add("Hospital Medical Report");
        // Create the second paragraph
        Paragraph tipsParagraph = new Paragraph();
        tipsParagraph.setFont(new Font(bfChinese, 10, Font.NORMAL));
        tipsParagraph.setAlignment(Element.ALIGN_CENTER);
        tipsParagraph.setLeading(tipsParagraph.getTotalLeading()+10);
        tipsParagraph.add("Print time: " + TodayUtil.getTodayYmd());

        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

        // Open the document
        document.open();
        // Set document title
        document.addTitle("Hospital");
        // Set document author
        document.addAuthor("NZHospital");
        document.addCreationDate();
        // Set keywords
        document.addKeywords("iText");
        document.addLanguage("English");
        // Add paragraphs to the document
        document.add(titleParagraph);
        document.add(tipsParagraph);
        // Table
        PdfPTable tableMessage = new PdfPTable(4);
        tableMessage.setSpacingBefore(8f);
        tableMessage.setSpacingAfter(8f);
        // Remove table borders
        tableMessage.getDefaultCell().setBorder(0);
        // Set table width
        tableMessage.setTotalWidth(new float[] { 30, 120, 30, 120 });
        tableMessage.addCell(new Paragraph("Name", FontChinese));
        tableMessage.addCell(new Paragraph(order.getPatient().getPName(), FontChinese));
        tableMessage.addCell(new Paragraph("Gender", FontChinese));
        tableMessage.addCell(new Paragraph(order.getPatient().getPGender(), FontChinese));
        tableMessage.addCell(new Paragraph("Age", FontChinese));
        tableMessage.addCell(new Paragraph(order.getPatient().getPAge() +" years", FontChinese));
        tableMessage.addCell(new Paragraph("Order No.", FontChinese));
        tableMessage.addCell(String.valueOf(order.getOId()));
        tableMessage.addCell(new Paragraph("Date", FontChinese));
        tableMessage.addCell(order.getOEnd());
        tableMessage.addCell(new Paragraph("Phone", FontChinese));
        tableMessage.addCell(order.getPatient().getPPhone());
        document.add(tableMessage);



        // Medical report table
        PdfPTable tableOrder = new PdfPTable(1);
        // Remove table borders
        tableOrder.getDefaultCell().setBorder(0);
        tableOrder.setSpacingBefore(30f);
        tableOrder.setSpacingAfter(10f);

        PdfPCell cell1 = new PdfPCell(new Paragraph("Symptoms", new Font(bfChinese, 14, Font.NORMAL)));
        cell1.setFixedHeight(25);
        cell1.setBorder(0);
        PdfPCell cell2 = new PdfPCell(new Paragraph(order.getORecord(), new Font(bfChinese, 10, Font.NORMAL)));
        cell2.setFixedHeight(30);
        cell2.setBorder(0);
        cell2.setPaddingLeft(10);
        PdfPCell cell3 = new PdfPCell(new Paragraph("Exam items and price", new Font(bfChinese, 14, Font.NORMAL)));
        cell3.setFixedHeight(25);
        cell3.setBorder(0);
        PdfPCell cell4 = new PdfPCell(new Paragraph(order.getOCheck(), new Font(bfChinese, 10, Font.NORMAL)));
        cell4.setFixedHeight(30);
        cell4.setBorder(0);
        cell4.setPaddingLeft(10);
        PdfPCell cell5 = new PdfPCell(new Paragraph("Drugs and price", new Font(bfChinese, 14, Font.NORMAL)));
        cell5.setFixedHeight(25);
        cell5.setBorder(0);
        PdfPCell cell6 = new PdfPCell(new Paragraph(order.getODrug(), new Font(bfChinese, 10, Font.NORMAL)));
        cell6.setFixedHeight(30);
        cell6.setBorder(0);
        cell6.setPaddingLeft(10);
        PdfPCell cell7 = new PdfPCell(new Paragraph("Diagnosis / doctor notes", new Font(bfChinese, 14, Font.NORMAL)));
        cell7.setFixedHeight(25);
        cell7.setBorder(0);
        PdfPCell cell8 = new PdfPCell(new Paragraph(order.getOAdvice(), new Font(bfChinese, 10, Font.NORMAL)));
        cell8.setFixedHeight(100);
        cell8.setBorder(0);
        cell8.setPaddingLeft(10);

        tableOrder.addCell(cell1);
        tableOrder.addCell(cell2);
        tableOrder.addCell(cell3);
        tableOrder.addCell(cell4);
        tableOrder.addCell(cell5);
        tableOrder.addCell(cell6);
        tableOrder.addCell(cell7);
        tableOrder.addCell(cell8);
        document.add(tableOrder);

        // Add logo, absolutely positioned at the top right
//        Image image = Image.getInstance("src/main/resources/static/images/dgut.jpeg");
//        image.setAbsolutePosition(440,690);
//        document.add(image);

        // Set PDF footer copyright
        PdfContentByte cb = writer.getDirectContent();
        BaseFont bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
        cb.beginText();
        cb.setFontAndSize(bf, 11);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,  "For reference only", 300, 40, 0);
        cb.setFontAndSize(bf,13);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,  "Copyright Hospital", 300, 20, 0);
        cb.endText();





        document.close();
    }
}
