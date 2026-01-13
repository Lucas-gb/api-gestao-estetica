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
            document.add(new Paragraph("Alergias: "
                    + (cliente.getAlergiasEObservacoes() != null ? cliente.getAlergiasEObservacoes() : "Nenhuma"),
                    fontCorpo));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    //GERAR MANUAL DE CONEXÃO FIREWALL IP
    public byte[] gerarManualConexao() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font fontCorpo = FontFactory.getFont(FontFactory.HELVETICA, 11);

            document.add(new Paragraph("MANUAL DE CONEXÃO MOBILE - BEAUTYMANAGER", fontTitulo));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("1. IDENTIFICAR O IP DO COMPUTADOR", fontSubtitulo));
            document.add(
                    new Paragraph("No terminal do Windows, digite 'ipconfig' e anote o Endereço IPv4.", fontCorpo));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("2. ACESSO PELO CELULAR", fontSubtitulo));
            document.add(new Paragraph("O celular deve estar no mesmo Wi-Fi. Use o link:", fontCorpo));
            document.add(new Paragraph("http://SEU_IP:8080/swagger-ui/index.html", fontCorpo));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("3. LIBERAÇÃO DO FIREWALL", fontSubtitulo));
            document.add(new Paragraph("Caso não conecte, crie uma Regra de Entrada no Firewall para a Porta TCP 8080.",
                    fontCorpo));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
}