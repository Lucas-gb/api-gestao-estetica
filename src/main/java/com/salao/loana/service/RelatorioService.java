package com.salao.loana.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.salao.loana.model.Cliente;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class RelatorioService {

    public byte[] gerarFichaAnamnese(Cliente cliente) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titulo = new Paragraph("FICHA DE ANAMNESE - ESTÉTICA", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph(" "));

            // Dados da Cliente
            Font fontCorpo = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("Nome: " + cliente.getNome(), fontCorpo));
            document.add(new Paragraph("Idade: " + cliente.getIdade(), fontCorpo));
            document.add(new Paragraph("Telefone: " + cliente.getTelefone(), fontCorpo));
            document.add(new Paragraph("Alergias: " + (cliente.getAlergiasEObservacoes() != null ? cliente.getAlergiasEObservacoes() : "Nenhuma"), fontCorpo));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}