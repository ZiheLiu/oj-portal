package com.ziheliu.ojbackend.dao.mybatis;

import com.ziheliu.ojbackend.model.entity.Submission;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SubmissionMapper {

  public int insertSubmission(Submission submission);

  public Submission selectSubmissionById(int submissionId);

  public List<Submission> selectSubmissions();
}
