package br.com.graphs.servlets;

import br.com.graphs.actions.CreateDatasetAction;
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
            String dirApp = request.getServletContext().getRealPath("");
            String tempDirFull = dirApp + File.separator + DIR_TEMP;

            File fileDirTemp = new File(tempDirFull);
            if (!fileDirTemp.exists()) {
                fileDirTemp.mkdir();
                fileDirTemp.setWritable(true);
                fileDirTemp.setReadable(true);
            }

            for (Part part : request.getParts()) {

                if (!part.getName().contains("submit")) {
                    String inputName = part.getName();

                    String fileSubmittedName = part.getSubmittedFileName();

                    String ext = fileSubmittedName.substring(fileSubmittedName.lastIndexOf('.') + 1);

                    if (!this.checkExtension(ext)) {
                        request.setAttribute("extensionError", true);
                        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
                    } else {
                        String fileName = inputName + "." + ext;

                        try {
                            File fileSubmitted = new File(tempDirFull + File.separator + fileName);

                            part.write(fileSubmitted.getAbsolutePath());

                            if (fileSubmitted.exists()) {
                                CreateDatasetAction createDataset = new CreateDatasetAction();

                                if (fileSubmitted.getName().contains("pre")) {                                                                       
                                    createDataset.createDatasetFlowchart(fileSubmitted, dirApp);
                                }
                                else {                                                                                                    
                                    File equivalencesFile = new File(tempDirFull + File.separator + "equivalences." + ext);
                                    File accompanimentsFile = new File(tempDirFull + File.separator + "accompaniments." + ext);                                    
                                    File historicalFile = new File(tempDirFull + File.separator + "historical." + ext);
                                    
                                    createDataset.createDatasetHistorical(equivalencesFile, accompanimentsFile, historicalFile, dirApp);
                                }
                            }
                        } catch (IOException e) {
                            response.getWriter().println("An error occurred while uploading: " + e.getMessage());
                        }
                    }
                }
            }
            
            request.setAttribute("uploadOK", true);
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    private boolean checkExtension(String ext) {
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
