package com.salao.loana.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.salao.loana.model.Cliente;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;

@Service
public class RelatorioService {

    public byte[] gerarFichaAnamnese(Cliente cliente) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 40, 40, 40, 40);

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // --- DATA RESPECTIVA DE EMISSAO DO DOCUMENTO ---
            String dataEmissao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            Paragraph pData = new Paragraph("Data de Emissão: " + dataEmissao,
                    FontFactory.getFont(FontFactory.HELVETICA, 9, Color.GRAY));
            pData.setAlignment(Element.ALIGN_RIGHT);
            document.add(pData);

            // --- FONTES ---
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Color.BLACK);
            Font fontSub = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.DARK_GRAY);
            Font fontSecao = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.WHITE);
            Font fontLabel = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font fontTexto = FontFactory.getFont(FontFactory.HELVETICA, 10);

            // --- CABEÇALHO ---
            Paragraph clinica = new Paragraph("Loana EstetiGarcia", fontTitulo);
            clinica.setAlignment(Element.ALIGN_CENTER);
            document.add(clinica);

            Paragraph subtitulo = new Paragraph("Ficha de Anamnese: Limpeza de Pele Profissional", fontSub);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitulo);
            document.add(new Paragraph(" "));

            // --- 1. DADOS PESSOAIS ---
            document.add(criarCabecalhoSecao("1. DADOS PESSOAIS", fontSecao));
            PdfPTable tabDados = new PdfPTable(2);
            tabDados.setWidthPercentage(100);
            tabDados.setSpacingBefore(5);

            tabDados.addCell(criarCelula("Nome: " + cliente.getNome(), fontTexto));
            tabDados.addCell(criarCelula("Data Nasc: " + cliente.getDataNascimento(), fontTexto));
            tabDados.addCell(criarCelula("Idade: " + cliente.getIdade() + " anos", fontTexto));
            tabDados.addCell(criarCelula("Profissão: " + cliente.getProfissao(), fontTexto));
            tabDados.addCell(criarCelula("Telefone: " + cliente.getTelefone(), fontTexto));
            tabDados.addCell(criarCelula("E-mail: " + cliente.getEmail(), fontTexto));

            PdfPCell cellEnd = criarCelula("Endereço: " + cliente.getEndereco(), fontTexto);
            cellEnd.setColspan(2);
            tabDados.addCell(cellEnd);

            document.add(tabDados);

            // 2. HISTÓRICO DE SAÚDE
            document.add(
                    new Paragraph("Sob tratamento médico? " + formatarSimNao(cliente.isTratamentoMedico()), fontTexto));
            if (cliente.isTratamentoMedico()) {
                document.add(new Paragraph("   Qual: " + cliente.getTratamentoMedicoQual(), fontTexto));
            }

            document.add(new Paragraph("Possui alergias? " + formatarSimNao(cliente.isPossuiAlergias()), fontTexto));
            if (cliente.isPossuiAlergias()) {
                document.add(new Paragraph("   Qual(is): " + cliente.getAlergiasQual(), fontTexto));
            }

            document.add(new Paragraph("Faz uso de ácidos ou clareadores? " + formatarSimNao(cliente.isUsaAcidos()),
                    fontTexto));
            if (cliente.isUsaAcidos()) {
                document.add(new Paragraph("   Qual: " + cliente.getUsaAcidosQual(), fontTexto));
            }

            // ATENÇÃO: Verifique se adicionou estes dois ao Cliente.java, senão o erro
            // continua:
            // private boolean usaMarcaPasso;
            // private boolean gestanteLactante;
            document.add(
                    new Paragraph("Usa marca-passo no rosto? " + formatarSimNao(cliente.isUsaMarcaPasso()), fontTexto));
            document.add(
                    new Paragraph("Gestante ou Lactante? " + formatarSimNao(cliente.isGestanteLactante()), fontTexto));

            document.add(new Paragraph("Possui problemas hormonais? " + formatarSimNao(cliente.isProblemasHormonais()),
                    fontTexto));
            if (cliente.isProblemasHormonais()) {
                document.add(new Paragraph("   Qual: " + cliente.getProblemasHormonaisQual(), fontTexto));
            }

            // --- 3. HÁBITOS ---
            document.add(new Paragraph(" "));
            document.add(criarCabecalhoSecao("3. HÁBITOS E ESTILO DE VIDA", fontSecao));
            document.add(new Paragraph("Exposição ao sol frequente? " + formatarSimNao(cliente.isExposicaoSol()),
                    fontTexto));
            document.add(new Paragraph("Usa filtro solar? " + formatarSimNao(cliente.isUsaFiltroSolar()), fontTexto));
            document.add(new Paragraph("Tabagismo? " + formatarSimNao(cliente.isTabagismo()), fontTexto));
            document.add(new Paragraph("Qualidade do sono: " + cliente.getQualidadeSono(), fontTexto));
            document.add(new Paragraph("Rotina de cuidados: " + cliente.getRotinaCuidados(), fontTexto));

            // --- 4. AVALIAÇÃO BIOTIPOLÓGICA ---
            document.add(new Paragraph(" "));
            document.add(criarCabecalhoSecao("4. AVALIAÇÃO BIOTIPOLÓGICA (EXAME FÍSICO)", fontSecao));

            PdfPTable tabBio = new PdfPTable(2);
            tabBio.setWidthPercentage(100);
            tabBio.setSpacingBefore(5);

            tabBio.addCell(criarCelula("Biotipo Cutâneo: " + cliente.getBiotipoCutaneo(), fontTexto));
            tabBio.addCell(criarCelula("Fototipo: " + cliente.getFototipo(), fontTexto));
            tabBio.addCell(criarCelula(" ", fontTexto));

            document.add(tabBio);

            // --- OBSERVAÇÕES ---
            document.add(new Paragraph(" "));
            document.add(new Paragraph("OBSERVAÇÕES ADICIONAIS:", fontLabel));
            document.add(new Paragraph(cliente.getObservacoesAdicionais(), fontTexto));

            // --- ASSINATURAS ---
            PdfPTable tabAssin = new PdfPTable(2);
            tabAssin.setWidthPercentage(100);
            tabAssin.setSpacingBefore(50);

            tabAssin.addCell(criarCelulaAssinatura("Assinatura da Paciente"));
            tabAssin.addCell(criarCelulaAssinatura("Profissional Responsável"));

            document.add(tabAssin);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    // Métodos Auxiliares para o Design
    private PdfPCell criarCabecalhoSecao(String texto, Font fonte) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, fonte));
        cell.setBackgroundColor(Color.BLACK);
        cell.setPadding(5);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private PdfPCell criarCelula(String texto, Font fonte) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, fonte));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(3);
        return cell;
    }

    private PdfPCell criarCelulaAssinatura(String rotulo) {
        PdfPTable table = new PdfPTable(1);
        PdfPCell linha = new PdfPCell(
                new Phrase("___________________________________", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        linha.setBorder(Rectangle.NO_BORDER);
        linha.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell texto = new PdfPCell(new Phrase(rotulo, FontFactory.getFont(FontFactory.HELVETICA, 9)));
        texto.setBorder(Rectangle.NO_BORDER);
        texto.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(linha);
        table.addCell(texto);

        PdfPCell mainCell = new PdfPCell(table);
        mainCell.setBorder(Rectangle.NO_BORDER);
        return mainCell;
    }

    private String formatarSimNao(boolean valor) {
        return valor ? "(X) Sim  ( ) Não" : "( ) Sim  (X) Não";
    }

    // Método do Manual de Conexão que já tínhamos (mantenha-o abaixo se desejar)
    public byte[] gerarManualConexao() {
        // ... (código do manual que você já tem)
        return new byte[0]; // simplificado para o exemplo
    }
}