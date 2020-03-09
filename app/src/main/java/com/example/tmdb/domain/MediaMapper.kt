package com.example.tmdb.domain

import com.example.tmdb.data.repository.remote.entity.Movie
import com.example.tmdb.data.repository.remote.entity.Serie

class MediaMapper {

    companion object{
        fun movieToMedia(movie: Movie): Media {
            val media = Media(
                movie.id,
                movie.title,
                movie.poster,
                movie.posterBack,
                movie.details,
                movie.releaseDate,
                movie.instant,
                MediaType.MOVIE
            )
            return media
        }

        fun mediaToMovie(media: Media): Movie {
            val movie = Movie(
                media.id,
                media.title,
                media.poster,
                media.posterWide,
                media.releaseDate,
                media.details,
                media.instant
            )
            return movie
        }
        fun serieToMedia(serie: Serie): Media {
            val media = Media(
                serie.id,
                serie.title,
                serie.poster,
                serie.posterWide,
                serie.details,
                serie.releaseDate,
                serie.instant,
                MediaType.SERIE
            )
            return media
        }

        fun mediaToSerie(media: Media): Serie {
            val serie = Serie(
                media.id,
                media.title,
                media.poster,
                media.details,
                media.posterWide,
                media.releaseDate,
                media.instant
            )
            return serie
        }
    }


}