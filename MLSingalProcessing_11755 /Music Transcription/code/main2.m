
BWNotes = ['+4'; '+5'; '+6'; '-3'; '-4'; '-5'; '-6'];
BWNotes = strcat('Blowing in the wind/Note_',BWNotes,'.wav');
MusicPath = 'Blowing in the wind/MusicalScale.wav';
OutputName = 'output2.wav';

N = size(BWNotes,1);
spectrumBWNotes = [];

for i = 1:N
  [spectrumBWNotes(i,:)] = getMusicCharacters(BWNotes(i,:));
end

[spectrumMusic, smagMusic, sphaseMusic] = getMusicCharacters(MusicPath);
projectedNoteSpect = [];  

 for i = 1:N;
    W = spectrumBWNotes(i,:).';
    projectedNoteSpect(i,:) = projectSpectrogram(W, smagMusic);
    projectedNoteSpect(i,:) = im2bw(projectedNoteSpect(i,:), mean(projectedNoteSpect(i,:)));
 end
 
a = (spectrumBWNotes' * projectedNoteSpect) .* sphaseMusic;
stf = stft(a,2048,256,0,hann(2048)); 
audiowrite(OutputName, stf, 10000);
 