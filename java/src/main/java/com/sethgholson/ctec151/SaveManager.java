package com.sethgholson.ctec151;

import com.sethgholson.ctec151.character.GameCharacter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

public class SaveManager {

  private final Moshi moshi;

  public SaveManager() {
    this.moshi = new Moshi.Builder().build();
  }

  public void saveToFile(String filename, Collection<GameCharacter> characters) throws IOException {
    ParameterizedType characterListType =
        Types.newParameterizedType(Collection.class, GameCharacter.class);
    JsonAdapter<Collection<GameCharacter>> jsonAdapter = moshi.adapter(characterListType);
    File saveFile = new File(filename + ".json");
    Sink fileSink = Okio.sink(saveFile);
    BufferedSink bufferedSink = Okio.buffer(fileSink);
    jsonAdapter.toJson(bufferedSink, characters);
    bufferedSink.close();
    fileSink.close();
  }

  public Collection<GameCharacter> loadFromFile(File file) throws IOException {
    ParameterizedType characterListType =
        Types.newParameterizedType(Collection.class, GameCharacter.class);
    JsonAdapter<Collection<GameCharacter>> jsonAdapter = moshi.adapter(characterListType);
    JsonReader source = JsonReader.of(Okio.buffer(Okio.source(file)));
    return jsonAdapter.fromJson(source);
  }
}
