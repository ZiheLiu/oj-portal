package com.ziheliu.dao.mybatis;

import com.ziheliu.model.entity.Submission;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SubmissionMapper {

  public int insertSubmission(Submission submission);

  public Submission selectSubmissionById(int submissionId);

  public List<Submission> selectSubmissions();

  public int hasUnfinishedSubmissions(@Param("username") String username,
                                      @Param("problemId") int problemId);
}
