package org.example.player;

import org.example.characters.Characterrr;

import java.util.ArrayList;
import java.util.SortedSet;

public class Account {

    private final Information information;
    private final ArrayList<Characterrr> characters;
    private int playedGames;

    public Account(ArrayList<Characterrr> characters, int playedGames, Information information) {
        this.information = information;
        this.characters = characters;
        this.playedGames = playedGames;
    }

    public int getNumberOfCharacters() {
        return characters.size();
    }

    public String getCharacterName (int index) {
        Characterrr character = characters.get(index);
        return character.toString();
    }

    public Characterrr getCharacter (int index) {
        return characters.get(index);
    }

    public Credentials getInformation() {
        return information.credentials;
    }

    public void increasePlayedGames() {
        playedGames++;
    }

    public void setPhotos() {
        for (Characterrr character : characters) {
            character.generateImagePath();
        }
    }

    public static class Information {
        private final Credentials credentials;
        private final SortedSet<String> favoriteGames;
        private final String playerName;
        private final String country;

        private Information(Builder builder) {
            this.credentials = builder.credentials;
            this.favoriteGames = builder.favoriteGames;
            this.playerName = builder.playerName;
            this.country = builder.country;
        }

        public static class Builder {
            private Credentials credentials;
            private SortedSet<String> favoriteGames;
            private String playerName;
            private String country;

            public Builder withCredentials(Credentials credentials) {
                this.credentials = credentials;
                return this;
            }

            public Builder withFavoriteGames(SortedSet<String> favoriteGames) {
                this.favoriteGames = favoriteGames;
                return this;
            }

            public Builder withPlayerName(String playerName) {
                this.playerName = playerName;
                return this;
            }

            public Builder withCountry(String country) {
                this.country = country;
                return this;
            }

            public Information build() {
                return new Information(this);
            }
        }

        @Override
        public String toString() {
            return "Information{" +
                    "credentials=" + credentials +
                    ", favoriteGames=" + favoriteGames +
                    ", playerName='" + playerName + '\'' +
                    ", country='" + country + '\'' +
                    '}';
        }
    }
}
