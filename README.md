# Masters

### Installation guide (linux, ubuntu server)

First apt-get all dependencies

```
sudo apt-get install -y build-essential curl git m4 ruby texinfo libbz2-dev libcurl4-openssl-dev libexpat-dev libncurses-dev zlib1g-dev default-jre openjdk-7-jdk python-software-properties libxml2-dev libxslt-dev byobu

sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java7-installer
```

Install brew for anything else
```
ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/linuxbrew/go/install)"
```

Add to your .bashrc or .zshrc:
```
export PATH="$HOME/.linuxbrew/bin:$PATH"
export MANPATH="$HOME/.linuxbrew/share/man:$MANPATH"
export INFOPATH="$HOME/.linuxbrew/share/info:$INFOPATH"
```

Continue brewing
```
brew doctor
brew update && upgrade
brew install caskroom/cask/brew-cask python freetype maven
```

Final step, python dependencies
```
pip install lxml Pillow networkx matplotlib numpy
```


# Lets see it running
### Python tests
```
python -m tests discover
```
### Maven install
Remember to install matsim first
```
mvn install -Dmaven.test.skip=true
mvn test -pl :p.lodz.ms -Dtest=AppTest
```