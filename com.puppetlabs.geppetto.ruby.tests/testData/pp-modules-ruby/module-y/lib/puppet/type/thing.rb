Puppet::Type.newtype(:thing) do
  BOOLEAN_DOC="Valid values are: False/0/No or True/1/Yes."

  @doc = "Description of thing"
    
  ensurable

  newparam(:name) do
    desc "Description of name"
    isnamevar
  end

  newparam(:flag) do
    desc "The flag. #{BOOLEAN_DOC}"
    isnamevar
  end
  
  newproperty(:weight) do
    desc "Description of weight"
  end
  newproperty(:empty)
  
end
