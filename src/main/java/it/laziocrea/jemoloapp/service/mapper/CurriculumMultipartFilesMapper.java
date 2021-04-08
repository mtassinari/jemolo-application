package it.laziocrea.jemoloapp.service.mapper;

import it.laziocrea.jemoloapp.domain.Curriculum;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CurriculumMultipartFilesMapper {
	
	private final Logger log = LoggerFactory.getLogger(CurriculumMultipartFilesMapper.class);
	
	public Set<Curriculum> multiPartFilesToCvs(List<MultipartFile> files){
		return files.stream()
				.map((this::multiPartFileToCv))
				.collect(Collectors.toSet());
	}

	public Curriculum multiPartFileToCv(MultipartFile file) {
		Curriculum cv = new Curriculum();
		cv.setCv(file.getOriginalFilename());
		cv.setSize(file.getSize());
		cv.setMimeType(file.getContentType());
		cv.setUrlAllegato("urlAllegato");
		cv.setNote("note");
		try {
			cv.addAllegato(file.getBytes());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return cv;
	}

}
