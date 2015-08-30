
function [hasConverged,ratio] = checkConvg(oldL,newL,eta,ratio)

  % TODO: complete the function
  abs(oldL - newL)/newL
  hasConverged = true;
  r = abs(oldL - newL)/abs(newL);
  ratio = [ratio;r];
  if r > eta
      hasConverged = false;
  end
end
