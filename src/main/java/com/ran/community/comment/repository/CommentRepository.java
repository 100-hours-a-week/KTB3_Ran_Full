package com.ran.community.comment.repository;

import com.ran.community.comment.dto.CommentDto;
import com.ran.community.comment.dto.CommentInputDto;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CommentRepository {
    private long index;
    private Map<Long, CommentDto> Comments = new LinkedHashMap<>();

    public CommentRepository(){
        this.index = 0;
    }

    //식별자로 찾기
    public Optional<CommentDto> getComment(long commentId){
        return Optional.of(Comments.get(commentId));
    }

    //댓글 생성
    public CommentDto commentCreate(long userId, long postId, CommentInputDto commentInputDto){
        CommentDto commentDto = new CommentDto();

        commentDto.setCommentId(index);
        commentDto.setAuthorId(userId);
        commentDto.setPostId(postId);
        commentDto.setPostTime();
        commentDto.setContent(commentInputDto.getComment());
        index++;
        Comments.put(commentDto.getCommentId(), commentDto);
        return commentDto;
    }

    //특정 게시물의 댓글 리스트 가져오기
    public Optional<List<CommentDto>> commentListByPostId(long postId){
        return Optional.of(Comments.values().stream().filter(it -> it.getPostId().equals(postId)).toList());
    }

    //댓글 수정
    public Optional<CommentDto> commentUpdate(CommentDto comment, CommentInputDto commentInputDto) {
        comment.setContent(commentInputDto.getComment());
        return Optional.of(comment);
    }

    //댓글 삭제
    public Optional<CommentDto> commentDelete(CommentDto comment) {
        return Optional.of(Comments.remove(comment.getCommentId()));
    }

}
