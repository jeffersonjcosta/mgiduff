package br.com.grafos.servlets;

import br.com.grafos.actions.CriarDatasetAction;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "UploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class UploadServlet extends HttpServlet {

    private final String DIR_TEMP = "temp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            String diretorioAplicacao = request.getServletContext().getRealPath("");
            String diretorioTemporarioCompleto = diretorioAplicacao + File.separator + DIR_TEMP;

            File fileDiretorioTemporario = new File(diretorioTemporarioCompleto);
            if (!fileDiretorioTemporario.exists()) {
                fileDiretorioTemporario.mkdir();
                fileDiretorioTemporario.setWritable(true);
                fileDiretorioTemporario.setReadable(true);
            }

            for (Part part : request.getParts()) {

                if (!part.getName().contains("enviar")) {
                    String nomeInput = part.getName();

                    String nomeArquivoEnviado = part.getSubmittedFileName();

                    String ext = nomeArquivoEnviado.substring(nomeArquivoEnviado.lastIndexOf('.') + 1);

                    if (!this.verificarExtensao(ext)) {
                        request.setAttribute("erroExtensao", true);
                        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
                    } else {
                        String nomeArquivo = nomeInput + "." + ext;

                        try {
                            File arquivoEnviado = new File(diretorioTemporarioCompleto + File.separator + nomeArquivo);

                            part.write(arquivoEnviado.getAbsolutePath());

                            if (arquivoEnviado.exists()) {
                                CriarDatasetAction criarDataset = new CriarDatasetAction();

                                if (arquivoEnviado.getName().contains("pre")) {                                                                       
                                    criarDataset.criarDatasetGradeCurricular(arquivoEnviado, diretorioAplicacao);
                                }
                                else {                                                                                                    
                                    File arquivoEquivalencias = new File(diretorioTemporarioCompleto + File.separator + "equivalencias." + ext);
                                    File arquivoAcompanhamentos = new File(diretorioTemporarioCompleto + File.separator + "acompanhamentos." + ext);                                    
                                    File arquivoHistoricos = new File(diretorioTemporarioCompleto + File.separator + "historicos." + ext);
                                    
                                    criarDataset.criarDatasetHistoricos(arquivoEquivalencias, arquivoAcompanhamentos, arquivoHistoricos, diretorioAplicacao);
                                }
                            }
                        } catch (IOException e) {
                            response.getWriter().println("Ocorreu um erro ao fazer o upload: " + e.getMessage());
                        }
                    }
                }
            }
            
            request.setAttribute("uploadOK", true);
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    private boolean verificarExtensao(String ext) {
        switch (ext) {
            case "TXT":
                return true;
            case "txt":
                return true;
            case "CSV":
                return true;
            case "csv":
                return true;
            case "":
                return true;
        }

        return false;
    }
}
