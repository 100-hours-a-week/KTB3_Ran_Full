//package com.ran.community.like.repository;
//
//import com.ran.community.global.LikeIdGenerator;
//import com.ran.community.like.entity.PostLike;
//import com.ran.community.post.entity.Post;
//import com.ran.community.user.entity.User;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicLong;
//
////@Repository
//public class InMemoryLikeRepository implements LikeRepository {
//    private final Map<Long, PostLike> Likes = new ConcurrentHashMap<>();
//
//    @Override
//    public Optional<PostLike> getLike(long likeId){
//        return Optional.ofNullable(Likes.get(likeId));
//    }
//
//
//    @Override
//    public PostLike addLike(User user, Post post){
//        PostLike like = new PostLike(LikeIdGenerator.getInstance().nextId(),user,post);
//        Likes.put(like.getId(), like);
//        return like;
//    }
//
//    @Override
//    public Optional<List<PostLike>> getLikeListByPostId(Post post){
//        return Optional.of(Likes.values().stream().filter(it -> it.getPost().getId() == post.getId()).toList());
//    }
//
//
//    @Override
//    public Optional<PostLike> deleteLike(Post post, User user) {
//        PostLike like = Likes.values().stream().filter(it->it.getUser().getUserId()==id && it.getPost().getPostId()==id).findFirst().orElse(null);
//        // 삭제
//        if (like.getUser().getUserId() != id) {
//            throw new IllegalArgumentException("본인이 누른 좋아요만 취소할 수 있습니다.");
//        }
//
//        Likes.remove(like.getLikeId());
//        return Optional.of(like);
//    }
//
//}
