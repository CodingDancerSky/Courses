
BWNotes = ['+4'; '+5'; '+6'; '-3'; '-4'; '-5'; '-6'];
BWNotes = strcat('Blowing in the wind/Note_',BWNotes,'.wav');
MusicPath = 'Blowing in the wind/music_extract.wav';
OutputName = 'output4.wav';

N = size(BWNotes,1);
spectrumBWNotes = [];

for i = 1:N
  [spectrumBWNotes(i,:)] = getMusicCharacters(BWNotes(i,:));
end


pianoNotes = ['a1';'e1';'e2';'f1';'h1';'i1';'n1';'r1';'s1';
              'n2';'n3';'o1';'o2';'o3';'o4';'p1';'t1';'w1'];
pianoNotes = strcat('piano/',pianoNotes,'.wav');

N2 = size(pianoNotes,1);
spectrumPianoNotes = [];

for i = 1:N2
  [spectrumPianoNotes(i,:)] = getMusicCharacters(pianoNotes(i,:));
end

pair = spectrumBWNotes * spectrumPianoNotes';
[maxValue, maxIndex] = max(pair,[],2);

newSpectrumNotes = [];
for i = 1:N
    newSpectrumNotes(i,:) = spectrumPianoNotes(maxIndex(i),:);
end

[spectrumMusic, smagMusic, sphaseMusic] = getMusicCharacters(MusicPath);
projectedNoteSpect = [];  

 for i = 1:N;
    W = newSpectrumNotes(i,:).';
    projectedNoteSpect(i,:) = projectSpectrogram(W, smagMusic);
    projectedNoteSpect(i,:) = im2bw(projectedNoteSpect(i,:), mean(projectedNoteSpect(i,:)));
 end
 
a = (newSpectrumNotes' * projectedNoteSpect) .* sphaseMusic;
stf = stft(a,2048,256,0,hann(2048)); 
audiowrite(OutputName, stf, 10000);

 