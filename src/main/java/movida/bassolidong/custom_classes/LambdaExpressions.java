package movida.bassolidong.custom_classes;

import movida.commons.Collaboration;
import movida.commons.Movie;

public class LambdaExpressions {
    public interface MovieSearchElem {
        // An abstract function
        boolean searchIn(Movie m);
    }

    public interface FieldROfP<R, P> {
        R getField(P m);
    }

}