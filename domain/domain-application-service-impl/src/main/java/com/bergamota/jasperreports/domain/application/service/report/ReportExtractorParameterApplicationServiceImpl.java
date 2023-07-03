package com.bergamota.jasperreports.domain.application.service.report;

import com.bergamota.jasperreports.domain.application.service.input.services.ReportExtractorParameterApplicationService;
import com.bergamota.jasperreports.domain.core.entities.ReportParameter;
import com.bergamota.jasperreports.domain.core.entities.ReportParameterView;
import com.bergamota.jasperreports.domain.core.exceptions.ReportDomainException;
import com.bergamota.jasperreports.domain.core.valueobjects.ReportParamType;
import org.springframework.stereotype.Service;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Service
public class ReportExtractorParameterApplicationServiceImpl implements ReportExtractorParameterApplicationService {


    private  String getCharacterDataFromElement(Element e) {
        if(e == null)
            return "";

        Node child = e.getFirstChild();
        if (child instanceof CharacterData cd) {
            return cd.getData();
        }
        return "";
    }
    public Set<ReportParameter> extractParametersFromJrXml(String xmlPath)  {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Set<ReportParameter> reportParameters = new HashSet<>();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(xmlPath));

            doc.getDocumentElement().normalize();

            Element jasperReportElement = (Element)doc.getElementsByTagName("jasperReport").item(0);

            NodeList list = jasperReportElement.getElementsByTagName("parameter");

            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    var name = element.getAttribute("name");
                    var classType = element.getAttribute("class");
                    var isPrompting = element.getAttribute("isForPrompting");
                    var descriptionElement = (Element)element.getElementsByTagName("parameterDescription").item(0);
                    var valueDefaultElement = (Element)element.getElementsByTagName("defaultValueExpression").item(0);
                    var description = getCharacterDataFromElement(descriptionElement);
                    var defaultValue = getCharacterDataFromElement(valueDefaultElement);

                    isPrompting = isPrompting.isEmpty() ? "true" : isPrompting;

                    reportParameters.add(ReportParameter.builder()
                                    .name(name)
                                    .defaultValue(defaultValue)
                                    .type(ReportParamType.toEnum(classType))
                                    .reportType(ReportParamType.toEnum(classType))
                                    .createdManually(false)
                                    .reportParameterView(ReportParameterView.builder()
                                            .label(description.isEmpty() ? name : description)
                                            .visible(Boolean.parseBoolean(isPrompting))
                                            .required(description.isEmpty())
                                            .build())
                            .build());
                }
            }

            return reportParameters;

        }catch (Exception ex){
            throw new ReportDomainException(ex.getMessage(), ex);
        }
    }
}
