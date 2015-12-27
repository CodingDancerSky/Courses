function [spectrumBWNotes, smagBWNotes] = processNotes(MusicPaths)
  
  [spectrum4p, smag4p] = getMusicCharacters('Blowing in the wind/Note_+4.wav');
  [spectrum5p, smag5p] = getMusicCharacters('Blowing in the wind/Note_+5.wav');
  [spectrum6p, smag6p] = getMusicCharacters('Blowing in the wind/Note_+6.wav');
  [spectrum3n, smag3n] = getMusicCharacters('Blowing in the wind/Note_-3.wav');
  [spectrum4n, smag4n] = getMusicCharacters('Blowing in the wind/Note_-4.wav');
  [spectrum5n, smag5n] = getMusicCharacters('Blowing in the wind/Note_-5.wav');
  [spectrum6n, smag6n] = getMusicCharacters('Blowing in the wind/Note_-6.wav');

  spectrumBWNotes = [spectrum4p; spectrum5p; spectrum6p;
                     spectrum3n; spectrum4n; spectrum5n; spectrum6n];
  smagBWNotes = [smag4p; smag5p; smag6p; 
                 smag3n; smag4n; smag5n; smag6n]

end
