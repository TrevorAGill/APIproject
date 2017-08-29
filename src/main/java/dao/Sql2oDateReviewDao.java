package dao;

import models.DateReview;
import models.Question;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDateReviewDao implements DateReviewDao {
    private final Sql2o sql2o;

    public Sql2oDateReviewDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(DateReview dateReview) {
        String sql = "INSERT INTO datereviews (postcontent, rating) VALUES (:postcontent, :rating)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("postcontent", dateReview.getPostContent())
                    .addParameter("rating", dateReview.getRating())
                    .addColumnMapping("POSTCONTENT", "postcontent")
                    .addColumnMapping("RATING", "rating")
                    .executeUpdate()
                    .getKey();
            dateReview.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<DateReview> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM datereviews")
                    .executeAndFetch(DateReview.class);
        }
    }

    @Override
    public List<DateReview> getAllDateReviewsOfSpecificUser(int userId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM datereviews WHERE id = :id")
                    .addParameter("id", userId)
                    .executeAndFetch(DateReview.class);
        }
    }
}
