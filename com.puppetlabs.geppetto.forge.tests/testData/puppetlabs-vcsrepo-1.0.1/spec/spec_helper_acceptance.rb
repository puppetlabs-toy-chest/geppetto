require 'beaker-rspec'

unless ENV['RS_PROVISION'] == 'no'
  hosts.each do |host|
    # Install Puppet
    if host.is_pe?
      install_pe
    else
      install_puppet
      on hosts, "mkdir -p #{hosts.first['distmoduledir']}"
    end

    # We ask the host to interpolate it's distmoduledir because we don't
    # actually know it on Windows until we've let it redirect us (depending
    # on whether we're running as a 32/64 bit process on 32/64 bit Windows
    moduledir = on(host, "echo #{host['distmoduledir']}").stdout.chomp
    on host, "mkdir -p #{moduledir}"
  end
end

RSpec.configure do |c|
  # Project root
  proj_root = File.expand_path(File.join(File.dirname(__FILE__), '..'))

  # Readable test descriptions
  c.formatter = :documentation

  # Configure all nodes in nodeset
  c.before :suite do
    # Install module and dependencies on all hosts
    puppet_module_install(:source => proj_root, :module_name => 'vcsrepo')

    # ensure test dependencies are available on all hosts
    hosts.each do |host|
      case fact_on(host, 'osfamily')
      when 'RedHat'
        install_package(host, 'git')
      when 'Debian'
        install_package(host, 'git-core')
      else
        if !check_for_package(host, 'git')
          puts "Git package is required for this module"
          exit
        end
      end
      on host, 'git config --global user.email "root@localhost"'
      on host, 'git config --global user.name "root"'
    end
  end
end
