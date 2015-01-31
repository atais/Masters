# Masters

Installation guide (linux, ubuntu server)

sudo apt-get install -y build-essential curl git m4 ruby texinfo libbz2-dev libcurl4-openssl-dev libexpat-dev libncurses-dev zlib1g-dev default-jre openjdk-7-jdk python-software-properties libxml2-dev libxslt-dev

sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java7-installer

ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/linuxbrew/go/install)"

Add to your .bashrc or .zshrc:
export PATH="$HOME/.linuxbrew/bin:$PATH"
export MANPATH="$HOME/.linuxbrew/share/man:$MANPATH"
export INFOPATH="$HOME/.linuxbrew/share/info:$INFOPATH"

brew update && upgrade
brew install caskroom/cask/brew-cask python freetype maven

pip install lxml Pillow networkx matplotlib numpy



# Check with tests:
python -m tests discover