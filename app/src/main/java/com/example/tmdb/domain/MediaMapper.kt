package com.example.tmdb.domain

import com.example.tmdb.data.repository.local.entity.FavoriteMedia
import com.example.tmdb.data.repository.remote.entity.Movie
import com.example.tmdb.data.repository.remote.entity.Serie

object MediaMapper {

    fun movieToMedia(movie: Movie) = Media(
            movie.id,
            movie.title,
            movie.poster,
            movie.posterBack,
            movie.details,
            movie.releaseDate,
            movie.instant,
            MediaType.MOVIE
        )

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

    fun mediaToFavorite(media: Media): FavoriteMedia {
        val favoriteMedia = FavoriteMedia(
            media.id,
            media.title,
            media.poster,
            media.details,
            media.posterWide,
            media.releaseDate,
            media.instant
        )
        return favoriteMedia
    }

    fun favoriteToMedia(favoriteMedia: FavoriteMedia): Media {
        val media = Media(
            favoriteMedia.id,
            favoriteMedia.title,
            favoriteMedia.poster,
            favoriteMedia.posterWide,
            favoriteMedia.details,
            favoriteMedia.releaseDate,
            favoriteMedia.instant,
            MediaType.MOVIE
        )
        return media
    }
}

