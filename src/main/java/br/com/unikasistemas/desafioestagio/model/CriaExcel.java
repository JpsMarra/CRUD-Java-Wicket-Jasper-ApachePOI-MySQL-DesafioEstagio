package br.com.unikasistemas.desafioestagio.model;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.wicket.util.file.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CriaExcel {

    private static final String fileName = "C:/teste/testeExcel.xlsx";

    public static void CriaExcel(Pessoa pessoa){

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheetPessoas = workbook.createSheet("Pessoas");

//Cria uma linha na Planilha.

        Row cabecalho = sheetPessoas.createRow((short)0);
        //adicionando bordas
        CellStyle style = workbook.createCellStyle(); //criei o CellStyle
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //texto centralizado
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); //não sei kk
        style.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index); //cor de fundo
        style.setBorderBottom(CellStyle.BORDER_THIN); //borda de baixo
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); //cor borda baixo
        style.setBorderLeft(CellStyle.BORDER_THIN); //borda da esquerda
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); //cor borda esquerda
        style.setBorderRight(CellStyle.BORDER_THIN); //borda direita
        style.setRightBorderColor(IndexedColors.BLACK.getIndex()); //cor borda direita
        style.setBorderTop(CellStyle.BORDER_THIN); //borda de cima
        style.setTopBorderColor(IndexedColors.BLACK.getIndex()); //cor borda cima
        //Cria as células na linha
        Cell cell;
        cell = cabecalho.createCell(0); //tive que criar cada célula do cabeçalho
        cell.setCellValue("Tipo de Pessoa"); //texto da celula 0
        cell.setCellStyle(style); //setando o Stlye para a célula
        sheetPessoas.autoSizeColumn((short) 0);
        cell = cabecalho.createCell(1); //e assim sucessivamente..
        cell.setCellValue("CPF/CNPJ");
        cell.setCellStyle(style);
        sheetPessoas.autoSizeColumn((short) 1);
        cell = cabecalho.createCell(2);
        cell.setCellValue("Razão Social");
        cell.setCellStyle(style);
        sheetPessoas.autoSizeColumn((short) 2);
        cell = cabecalho.createCell(3);
        cell.setCellValue("Nome Alternativo");
        cell.setCellStyle(style);
        sheetPessoas.autoSizeColumn((short) 3);
        cell = cabecalho.createCell(4);
        cell.setCellValue("Inscrição Estadual");
        cell.setCellStyle(style);
        sheetPessoas.autoSizeColumn((short) 4);
        cell = cabecalho.createCell(5);
        cell.setCellValue("RG");
        cell.setCellStyle(style);
        sheetPessoas.autoSizeColumn((short) 5);
        cell = cabecalho.createCell(6);
        cell.setCellValue("Telefone");
        cell.setCellStyle(style);
        sheetPessoas.autoSizeColumn((short) 6);
        cell = cabecalho.createCell(7);
        cell.setCellValue("Data de Nascimento");
        cell.setCellStyle(style);
        sheetPessoas.autoSizeColumn((short) 7);
        cell = cabecalho.createCell(8);
        cell.setCellValue("Situação");
        cell.setCellStyle(style);
        sheetPessoas.autoSizeColumn((short) 8);
        cell = cabecalho.createCell(9);
        cell.setCellValue("Endereços");
        cell.setCellStyle(style);
        sheetPessoas.autoSizeColumn((short) 9);

        List<Endereco> listaEnderecos = pessoa.getEnderecos();

        int rownum = 1;
        Row row = sheetPessoas.createRow(rownum++);
        int cellnum = 0;
        Cell cellTipoPessoa = row.createCell(cellnum++);
        cellTipoPessoa.setCellValue(String.valueOf(pessoa.getTipoPessoa()));
        Cell cellCpfCnpj = row.createCell(cellnum++);
        cellCpfCnpj.setCellValue(pessoa.getCpfCnpj());
        Cell cellRazaoSocial = row.createCell(cellnum++);
        cellRazaoSocial.setCellValue(pessoa.getRazaoSocial());
        Cell cellNomeAlternativo = row.createCell(cellnum++);
        cellNomeAlternativo.setCellValue(pessoa.getNomeAlternativo());
        Cell cellInscricaoEstadual = row.createCell(cellnum++);
        cellInscricaoEstadual.setCellValue(pessoa.getInscricaoEstadual());
        Cell cellRg = row.createCell(cellnum++);
        cellRg.setCellValue(pessoa.getRg());
        Cell cellTelefone = row.createCell(cellnum++);
        cellTelefone.setCellValue(pessoa.getTelefone());
        Cell cellDataNascimento = row.createCell(cellnum++);
        cellDataNascimento.setCellValue(pessoa.getDataNascimento());
        Cell cellAtivo = row.createCell(cellnum++);
        cellAtivo.setCellValue(pessoa.getAtivoNome());

        for (Endereco endereco : listaEnderecos) {
            rownum++;
            Cell cellCep = row.createCell(cellnum++);
            cellCep.setCellValue(endereco.getCep());
            Cell cellLogradouro = row.createCell(cellnum++);
            cellLogradouro.setCellValue(endereco.getLogradouro());
            Cell cellBairro = row.createCell(cellnum++);
            cellBairro.setCellValue(endereco.getBairro());
            Cell cellNumero = row.createCell(cellnum++);
            cellNumero.setCellValue(endereco.getNumero());
            Cell cellComplemento = row.createCell(cellnum++);
            cellComplemento.setCellValue(endereco.getComplemento());
            Cell cellEstado = row.createCell(cellnum++);
            cellEstado.setCellValue(endereco.getEstado());
            Cell cellCidade = row.createCell(cellnum++);
            cellCidade.setCellValue(endereco.getCidade());
            Cell cellEnderecoPrincipal = row.createCell(cellnum++);
            cellEnderecoPrincipal.setCellValue(endereco.getEnderecoPrincipalNome());
        }

        try {
            FileOutputStream out =
                    new FileOutputStream(new File(CriaExcel.fileName));
            workbook.write(out);
            out.close();
            System.out.println("Arquivo Excel criado com sucesso!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Arquivo não encontrado!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro na edição do arquivo!");
        }

    }

}
