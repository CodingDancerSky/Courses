
BWNotes = ['a1'; 'b1'; 'c1'; 'd1'; 'e1'; 'f1'; 'g1'; 'a2'; 'e2'; 'f2'; 'g2'];
BWNotes = strcat('PolyushkaPolye/',BWNotes,'.wav');
MusicPath = 'PolyushkaPolye/polyushka.wav';
OutputName = 'output3.wav';

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
 