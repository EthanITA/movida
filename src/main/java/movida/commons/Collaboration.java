package movida.commons;

import java.util.List;

public class Collaboration {

	Person actorA;
	Person actorB;
	List<Movie> movies;

	public Collaboration(Person actorA, Person actorB, List<Movie> movies) {
		this.actorA = actorA;
		this.actorB = actorB;
		this.movies = movies;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public Person getActorA() {
		return actorA;
	}

	public Person getActorB() {
		return actorB;
	}

	public Double getScore() {

		Double score = 0.0;

		for (Movie m : movies)
			score += m.getVotes();

		return score / movies.size();
	}

}
