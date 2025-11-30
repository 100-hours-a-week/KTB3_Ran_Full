package com.ran.community.comment.service;

import com.ran.community.comment.dto.request.CommentUpdatedFormDto;
import com.ran.community.comment.dto.response.CommentDataDto;
import com.ran.community.comment.entity.Comment;
import com.ran.community.comment.dto.request.CommentInputDto;
import com.ran.community.comment.repository.CommentRepository;
import com.ran.community.post.entity.Post;
import com.ran.community.post.repository.PostRepository;
import com.ran.community.user.entity.User;
import com.ran.community.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {

        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    private User findByUserEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }


    //식별자로 찾기
    private Post findByPostId(Long id) {
        return postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("게시물을 찾을 수 없습니다."));
    }



    //식별자로 댓글 찾기
    private Comment findByCommentId(long id){
        return commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("댓글이 없습니다."));
    }

    //private
    //본인이 작성한 댓글만 상태를 변경할 수 있음.(본인확인)
    //해당 클래스 안에서만 유의미한 메서드이기 때문에 다른 클래스에서 검증로직을 무분별하게 사용할 수 있음.
    private void validationUser(String email, long postId, Comment comment){
        if(!Objects.equals(comment.getUser().getEmail(), email) || comment.getPost().getId()!=postId){
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }
    }

    //
    private List<Comment> findByPost_Id(long postId){
        return commentRepository.findByPost_Id(postId);
    }


    //특정 게시글(id)의 댓글 전체 조회 fetch join
    @Transactional
    public List<CommentDataDto> commentListByPostId(long postId) {
        List<Comment> comments = findByPost_Id(postId);
        return comments.stream().map(comment -> new CommentDataDto(comment.getCommentId(),comment.getContent(),comment.getUser().getId())).collect(Collectors.toList());
    }

    //댓글 생성
    @Transactional
    public CommentDataDto commentCreate(String email, long postId, CommentInputDto commentInputDto) {
        User user = findByUserEmail(email);
        Post post = findByPostId(postId);
        post.increaseCommentCount();//댓글 갯수 증가

        Comment cmt = commentRepository.save(new Comment(commentInputDto.getContent(),user,post));
        return new CommentDataDto(cmt);
    }

    //댓글 수정
    @Transactional
    public CommentDataDto commentUpdate(String email, long postId, long commentId, CommentInputDto commentInputDto) {
        //내껏만 가능
        Comment comment = findByCommentId(commentId);
        validationUser(email,postId,comment);//수정권한 확인

        CommentUpdatedFormDto commentUpdatedFormDto = new CommentUpdatedFormDto(commentInputDto.getContent());
        comment.updateComment(commentUpdatedFormDto);
        return new CommentDataDto(comment);

    }

    //댓글 삭제
    @Transactional
    public CommentDataDto commentDelete(String email, long postId, long commentId) {
        Comment comment = findByCommentId(commentId);

        validationUser(email,postId,comment);//수정권한 확인

        Post post = findByPostId(postId);
        post.decreaseCommentCount();//댓글 갯수 감소

        commentRepository.deleteById(commentId);
        return new CommentDataDto(comment);
    }



}
