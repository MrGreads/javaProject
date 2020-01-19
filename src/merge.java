import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

class merge {
    public static void main(String[]args){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        Document doc = null;
        Document doc2 = null;

        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(new File("C:\\Users\\monst\\OneDrive\\Рабочий стол\\Новая папка\\catalog .xml"));
            doc2 = db.parse(new File("C:\\Users\\monst\\OneDrive\\Рабочий стол\\Новая папка\\catalog (4).xml"));
            NodeList ndListFirstFile = doc.getElementsByTagName("CD");
            NodeList ndListDonwloadFile = doc2.getElementsByTagName("CD");


            for (int i=0;i<ndListFirstFile.getLength();i++){
                NodeList f= doc.getElementsByTagName("TITLE");
                String full =f.item(i).getTextContent().trim();
                for(int n = 0; n<ndListDonwloadFile.getLength(); n++){
                    NodeList d=doc2.getElementsByTagName("TITLE");
                    String dell =d.item(i).getTextContent().trim();
                    if(full.equals(dell)){
                        Element deleted = (Element) doc.getElementsByTagName("CD").item(i);
                        deleted.getParentNode().removeChild(deleted);
                        System.out.println("yep");
                    }
                    else{
                        System.out.println(full);
                    }
                }

            }
            for(int i=0;i<ndListDonwloadFile.getLength();i++){
                Node nodeCD = doc.importNode(doc2.getElementsByTagName("CD").item(i), true);
                ndListFirstFile.item(i).appendChild(nodeCD);
            }

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new StringWriter());
            transformer.transform(source, result);

            Writer output = new BufferedWriter(new FileWriter("C:\\Users\\monst\\OneDrive\\Рабочий стол\\Новая папка\\catalogfin.xml"));
            String xmlOutput = result.getWriter().toString();
            output.write(xmlOutput);
            output.close();

        } catch (ParserConfigurationException | IOException | TransformerException | SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
