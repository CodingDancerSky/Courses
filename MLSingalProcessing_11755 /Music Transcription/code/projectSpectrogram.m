function projectedSpectrum = projectSpectrogram(W, M)
  % W*pinv(W)*M
  
  projectedSpectrum = W * pinv(W) * M;
  projectedSpectrum = mean(projectedSpectrum,1);
end