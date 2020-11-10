#Permet d'utiliser repo dans le terminal
mkdir -p ~/.bin
PATH="${HOME}/.bin:${PATH}"
curl https://storage.googleapis.com/git-repo-downloads/repo> ~/.bin/repo
chmod a+rx ~/.bin/repo

#Initialise le repo
repo init https://android.googlesource.com/kernel/manifest -b hikey-linaro-android-4.19

#Synchronise le repo
repo sync

#Se déplace dans le bon dossier du noyau
cd hikey-linaro/arch/

#Copie la config ranchu dans arm64
cp mips/configs/generic/board-ranchu.config arm64/configs/