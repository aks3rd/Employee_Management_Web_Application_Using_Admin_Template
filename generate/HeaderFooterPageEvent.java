import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class HeaderFooterPageEvent extends PdfPageEventHelper
{
public int x=0;
public void onStartPage(PdfWriter writer,Document document)
{
try
{

x++;
// TOP LEFT
Image logo;
logo=Image.getInstance("../images/tmlogo.png");
logo.setAlignment(Element.ALIGN_LEFT);
logo.setAbsolutePosition(30.0f,800.0f);
logo.scalePercent(50.5f,30.5f);
writer.getDirectContent().addImage(logo);
//document.add(logo);

Rectangle rect=writer.getBoxSize("rectangle");
Font firmNameFont=new Font(Font.FontFamily.TIMES_ROMAN,16,Font.BOLD);
Font titleFont=new Font(Font.FontFamily.TIMES_ROMAN,14,Font.NORMAL);

// TOP MEDIUM
ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Java-Based-Web-Service-Framework",firmNameFont),rect.getRight() / 2,rect.getTop(),0);
// TOP RIGHT
ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Page No. "+x,titleFont),rect.getRight(),rect.getTop(),0);
// TOP LEFT
ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase(new Chunk(new LineSeparator())), rect.getLeft(),rect.getTop()-10,0);

ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("    "), rect.getLeft(),rect.getTop()-10,0);

/*
String firmName="Thinking Machines";
Font firmNameFont=new Font(Font.FontFamily.TIMES_ROMAN,16,Font.BOLD);
Font titleFont=new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD);

Paragraph paragraph=new Paragraph(firmName,firmNameFont);
paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
document.add(paragraph);
// add page number
paragraph=new Paragraph("Page No. "+x);
paragraph.setAlignment(Element.ALIGN_RIGHT);
document.add(paragraph);

document.add(new Chunk(new DottedLineSeparator()));

*/
document.add(new Paragraph("\n"));

}catch(Exception e)
{
e.printStackTrace();
} 
}
public void onEndPage(PdfWriter writer,Document document) 
{
Rectangle rect1 = writer.getBoxSize("rectangle");
// BOTTOM LEFT
ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase(new Chunk(new LineSeparator())),rect1.getLeft(),rect1.getBottom(),0);
// BOTTOM MEDIUM
Font titleFont=new Font(Font.FontFamily.TIMES_ROMAN,8,Font.NORMAL);
ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Copyright : akashsoni 2020-2021",titleFont),rect1.getRight()/2,rect1.getBottom()-10,0);

PdfContentByte canvas = writer.getDirectContent();
        Rectangle rect = document.getPageSize();
        rect.setBorder(Rectangle.BOX); // left, right, top, bottom border
        rect.setBorderWidth(5); // a width of 5 user units
        rect.setBorderColor(BaseColor.BLACK); // a red border
        rect.setUseVariableBorders(true); // the full width will be visible
        canvas.rectangle(rect);
}
} 
