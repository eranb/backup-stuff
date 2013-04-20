def counting_sort a, b = [], c = []
  k = a.max
     
  for i in 0.upto(k) do
    c[i] = 0
  end

  for j in 0.upto(a.length-1) do
    c[a[j]] += 1
  end
 
  for i in 1.upto(k) do
    c[i] = c[i] + c[i-1]
  end
  
  for j in (a.length-1).send(:downto,0) do
    b[c[a[j]]] = a[j]
    c[a[j]] = c[a[j]] - 1
  end
  
  b.shift
  
  return b
end

p counting_sort [3,7,8,1,8,3,9,1,8,7,8]

#counting_sort [2,5,3,0,2,3,0,3]