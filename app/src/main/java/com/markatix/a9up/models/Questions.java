package com.markatix.a9up.models;

import java.util.List;

/**
 * Created by rohit on 28/8/16.
 */
public class Questions {

    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public class Data
    {

        private String question;
        private List<Answers> answers;

        private Figure figure;

        public Figure getFigure() {
            return figure;
        }

        public String getQuestion() {
            return question;
        }

        public List<Answers> getAnswers() {
            return answers;
        }

        public class Answers
        {
           private String content;
           private Boolean correct;

            public String getContent() {
                return content;
            }

            public Boolean getCorrect() {
                return correct;
            }
        }

        public class Figure
        {
            String url;
            Boolean exists;

            public String getUrl() {
                return url;
            }

            public Boolean getExists() {
                return exists;
            }
        }
    }
}
