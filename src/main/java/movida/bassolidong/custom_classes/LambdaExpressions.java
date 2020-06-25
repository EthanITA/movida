package movida.bassolidong.custom_classes;

import movida.commons.Movie;

public class LambdaExpressions {
    public interface MovieSearchElem {
        // An abstract function
        boolean searchIn(Movie m);
    }

    public interface MovieGetIntegerField {
        Integer getField(Movie m);
    }
}