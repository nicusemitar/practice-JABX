package org.example.unit;

import org.example.spring.model.Product;
import org.example.spring.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class ProductToXmlTest {

    private Product product;
    private StringWriter sw;

    @BeforeEach
    void setUp() {

        sw = new StringWriter();

        User user = new User();
        user.setId(1L);
        user.setLastName("John");

        product = new Product();
        product.setId(1L);
        product.setName("iPhone X");
        product.setPrice(1000L);
        product.setPrice(1);
        product.setUser(user);
    }

    @Test
    void testMarshalProductToXml() throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(Product.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(product, new StreamResult(sw));
        marshaller.marshal(product, new File("product.xml"));
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<product id=\"1\">\n" +
                "    <name>iPhone X</name>\n" +
                "    <quantity>0</quantity>\n" +
                "    <price>1</price>\n" +
                "    <boughtBy>\n" +
                "        <userId>1</userId>\n" +
                "        <userLastName>John</userLastName>\n" +
                "    </boughtBy>\n" +
                "</product>\n", sw.toString());

    }

    @Test
    void testUnmarshalXmlToProduct() throws JAXBException {

        File file = new File("product.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        product = (Product) unmarshaller.unmarshal(file);
        assertEquals("iPhone X", product.getName());

    }
}
