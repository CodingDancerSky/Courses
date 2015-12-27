% Check whether the objective value has converged 
% by comparing the difference between consecutive 
% objective values to the tolerance

function hasConverged = LR_CheckConvg(oldObj,newObj,tol)

  % TODO: complete the function
  hasConverged = true;
  if(abs(oldObj - newObj) > tol)
      hasConverged = false;
end
