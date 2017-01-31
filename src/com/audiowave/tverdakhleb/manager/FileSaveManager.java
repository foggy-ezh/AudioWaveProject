package com.audiowave.tverdakhleb.manager;

import com.audiowave.tverdakhleb.exception.FailedManagerWorkException;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class FileSaveManager {

    private static final String ABSOLUTE_PATH = "C:/music/";
    private static final String PROJECT_MUSIC_URI = "/project/music/";
    private static final String FILE_NAME = "filename";
    private static final String CONTENT_DISPOSITION = "content-disposition";


    public String saveAlbumCover(Part partCover, Long albumId, String albumName) throws FailedManagerWorkException {
        String fileName = extractFileName(partCover);
        System.out.println("filename1 "+fileName);
        String fullName = albumAbsolutePath(albumId,albumName) + File.separator + fileName;
        deletePrevious(fullName);
        System.out.println("fullName " + fullName);
        try {
            partCover.write(fullName);
        } catch (IOException e) {
            throw new FailedManagerWorkException(e);
        }
        return fullName.replace(ABSOLUTE_PATH, PROJECT_MUSIC_URI);
    }
    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String albumAbsolutePath(Long albumId, String albumName){
        // constructs path of the directory to save uploaded file
        String savePath = ABSOLUTE_PATH + albumId + albumName;
        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        return savePath;
    }
    private void deletePrevious( String fullName){
        // Delete previous file if exists
        File fileSaveDir = new File(fullName);
        if (fileSaveDir.exists()) {
            fileSaveDir.delete();
        }
    }
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader(CONTENT_DISPOSITION);
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith(FILE_NAME)) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
