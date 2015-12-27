
function [spectrum, smag, sphase] = getMusicCharacters(MusicName)
    %read a wav file
    [s,fs] = wavread(MusicName); //data; sample rate
    s = resample(s,16000,fs);
    if size(s,2) == 2 
      s = mean(s,2);
    end
  
    %The recordings of the notes can be computed to a spectrum 
    spectrum = mean(abs(stft(s',2048,256,0,hann(2048))),2);
    %'spectrum' will be a 1025 x 1 vector

    %The recordings of the complete music can be read just as you read the notes. 
    %To convert it to a spectrogram, do the following:
    sft = stft(s',2048,256,0,hann(2048)); 
    sphase = sft./abs(sft);
    smag = abs(sft);
    %'smag' will be a 1025 x K matrix where K is the number of spectral vectors in the matrix. 

end