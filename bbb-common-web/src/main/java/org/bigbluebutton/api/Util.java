package org.bigbluebutton.api;

import java.io.File;

import org.apache.commons.codec.digest.DigestUtils;

public final class Util {
    
    private Util() {
      throw new IllegalStateException("Utility class");
    }
	
	public static String generatePresentationId(String name) {
		long timestamp = System.currentTimeMillis();		
		return DigestUtils.sha1Hex(name) + "-" + timestamp;
	}
	
    public static String createNewFilename(String presId, String fileExt) {
        return presId + "." + fileExt;
    }
	
	public static File createPresentationDir(String meetingId, String presentationDir, String presentationId) {
		String meetingPath = presentationDir + File.separatorChar + meetingId + File.separatorChar + meetingId;
		String presPath = meetingPath + File.separatorChar + presentationId;
		File dir = new File(presPath);
		if (dir.mkdirs()) {
			return dir;
		}
		return null;
	}

	public static File getPresentationDir(String presentationBaseDir, String meetingId, String presentationId) {
		String meetingPath = presentationBaseDir + File.separatorChar + meetingId + File.separatorChar + meetingId;
		String presPath = meetingPath + File.separatorChar + presentationId;
		File dir = new File(presPath);
		if (dir.exists()) {
			return dir;
		}
		return null;
	}

	public static File downloadPresentationDirectory(String uploadDirectory) {
		File dir = new File(uploadDirectory + File.separatorChar + "download");
		if (dir.mkdirs()) {
			return dir;
		}
		return null;
	}

	public static String generateUploadId(String filename) {
		long timestamp = System.currentTimeMillis();
		return DigestUtils.sha1Hex(filename) + "-" + timestamp;
	}

	public static File createUploadDir(String rootDir, String source, String meetingId, String uploadId) {
		String meetingsPath = rootDir + File.separatorChar + "meetings";
		String sourcePath = meetingsPath + File.separatorChar + meetingId + File.separatorChar + source;
		String uploadPath = sourcePath + File.separatorChar + uploadId;
		File uploadDir = new File(uploadPath);
		if (uploadDir.mkdirs()) {
			return uploadDir;
		}
		return null;
	}

	public static String getDownloadPath(String rootDir, String source, String meetingId, String uploadId) {
		String meetingsPath = rootDir + File.separatorChar + "meetings";
		String sourcePath = meetingsPath + File.separatorChar + meetingId + File.separatorChar + source;
		String downloadPath = sourcePath + File.separatorChar + uploadId;
		return downloadPath;
	}
}
