
#!/bin/bash
#Ce script permet dinstaller repo dans le fichier PATH(De façon permanente), la commande repo devrait être utilisable de n'importe quel endroit ensuite.



mkdir ~/bin
echo 'export PATH=$PATH:~/bin' >> /etc/.bashrc
curl https://raw.githubusercontent.com/esrlabs/git-repo/stable/repo > ~/bin/repo
chmod +x ~/bin/repo

