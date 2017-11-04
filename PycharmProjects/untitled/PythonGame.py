"""FINALIZED PROJECT FOR COS 125"""

import os
import pygame
import sys

from pygame.locals import *

"""------------------------------------------------------------------------------------------------------"""

# GLOBAL VARIABLES AND OBJECTS

global clock
global gameTime

# ID DICTIONARIES
MENU_ID = {'TITLE': 0, 'HELP': 1, 'INGAME': 2, 'DEATH': 3}

# GAME CONSTANTS
GRAVITY = 1.43
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
SKYBLUE = (135, 206, 235)
GRAY = (150, 150, 150)
FPS = 40
level1map = [
    "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG",
    "G                              GCC                                             G",
    "G                              GCC  E E   D      G                             G",
    "G                              GGGGGGGGGGGGGGG  GG                             G",
    "G                              G         C       G                             G",
    "G         C                    G    GGGGGGGGGGGGGG                             G",
    "G        C C                   G      C          G            C       C        G",
    "GP      C   C                  GGGGGGGGGGGGG     G            C       C        G",
    "GGGGGGGGG    C                 G         CC CC   G                             G",
    "G                              G    GGGGGGGGGGGGGG            G   E O G        G",
    "G                              G                 G            GS      G        G",
    "G                              GGGGGGGGGGGG      GGGG E       GGG   GGG        G",
    "GCC D           E                           GG   G                G            G",
    "GSE D                    E                       G              GGGGG          G",
    "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG"
]

level2map = [
    "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG",
    "G                          G                                       G           G",
    "G                          G                                       GO          G",
    "G                          G              EEE                      G           G",
    "G                          G          GGGGGGGGGGGGGGGGGGGGGG       GGGGGG      G",
    "G                          G      GGGGG                            G        G  G",
    "G       G  G               G C        G                            G       GGCCG",
    "GP      G  GCCC            GGGGG      G       GGGGGGGGGGGGGGGGGGGGGG      GGGGGG",
    "GGGGGGGGG  GGGGGGGG        G       CC G              G         G               G",
    "G     D                    G      GGGGG              G         GCC  E   G      G",
    "G     D                    G          GGGGGGGGG      G         GCC    E GGGG   G",
    "G     D                    GGGGG C    G              G         GGGGGGGGGG      G",
    "G   D D D                      GGG    G                  C C                GGGG",
    "GS ED   D           E                 G        E            E                  G",
    "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG"
]

level3map = [
    "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII",
    "I                     I                CC               I  S           CC      I",
    "I                     I               DDDD              I  C           III     I",
    "I   C   IIIIII        IC         IIIIIIIIIIIIII         I  IO  E  D II         I",
    "I   IIIII          E  IIIIII     I                      I  I        IC    IIIIII",
    "I       IC         IIII       I EI                ECC   I  IIIIIIIIII          I",
    "IIII    IIIIII        I      IIIII            IIIIIIIIIII           IIIIII     I",
    "IP      I             I CII      I                      I                      I",
    "IIIIIIIII       CCC   IIIIII     IIIIIIIIIIII           I          IIIIIIIIIIIII",
    "I            IIIIIIIIII          I                    CCI        III           I",
    "I            I           E III   I         IIIIIIIIIIIIII               III    I",
    "I            IES D      IIIIIIIIII                      IIIIIIIIIIIIII         I",
    "I            IIIIIII        CIC                                            IIIII",
    "ICC                    IE   CIS                                                I",
    "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII"
]

level4map = [
    "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII",
    "I                                                      I                     E I",
    "I                                                      I   IIIIIIIIIII  IIIIIIII",
    "I                                                      I E           I         I",
    "I                                                      IIIIIIIIIII  II E     D I",
    "I                                                      I             IIIIIIIII I",
    "I       I  I           I                               I   IIIIIIIIIII         I",
    "IP      I  I E         II                              I             I D E  E  I",
    "IIIIIIIIIDDIIIIIIIIIIIIIII                             IIIIIIIIII   II IIIIIIIII",
    "IDDDDDDDD  DDDDDDDDDDDDDDII            CSSSC           I             I   E     I",
    "IDDDDDDDD  DDDDDDDDDDDDDDDII                           I   IIIIIIIIIII IIIIIIIII",
    "IDDDDDDDD     DDDDDDDDDDDDDII       D E  E  E D        I          DDDI    P    I",
    "I             DDDDDDDDDDDDDDII      IIIIIIIIIII        III        DDDI    D  O I",
    "IS         D                      D             D           E     DDDI    DD   I",
    "IIIIIIIII  IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII"
]


def RelRect(entity, camera):
    return pygame.Rect(entity.rect.x - camera.rect.x, entity.rect.y - camera.rect.y, entity.rect.w, entity.rect.h)


def loadImage(name, form):
    string = "assets\\%s.%s" % (name, form)
    image = None
    if form == "png":
        image = pygame.image.load(string).convert_alpha()
    else:
        image = pygame.image.load(string).convert()
    return image, image.get_rect()


def loadImages():
    backgroundImages[0], rect = loadImage('background\\beach', 'jpeg')
    backgroundImages[1], rect = loadImage('background\\beach2', 'jpg')
    backgroundImages[2], rect = loadImage('background\\beach3', 'jpg')
    backgroundImages[3], rect = loadImage('background\\beach4', 'jpg')

    enemyImages[1][0], rect = loadImage('enemyactions\\enemyLeftStep', 'png')
    enemyImages[1][1], rect = loadImage('enemyactions\\enemyIdle', 'png')
    enemyImages[1][2], rect = loadImage('enemyactions\\enemyRightStep', 'png')
    enemyImages[0][0] = pygame.transform.flip(enemyImages[1][0], True, False)
    enemyImages[0][1] = pygame.transform.flip(enemyImages[1][1], True, False)
    enemyImages[0][2] = pygame.transform.flip(enemyImages[1][2], True, False)

    tileImages[0], rect = loadImage('tiles\\grass', 'png')
    tileImages[1], rect = loadImage('tiles\\dirt', 'png')
    tileImages[2], rect = loadImage('tiles\\ice', 'png')


def drawBackground(screen):
    screen.fill(color)
    screen.blit(backImage, WINDOW)


def advanceMenu(newMenu, display):
    global currentState
    advance_menu = MENU_ID[newMenu]
    if advance_menu == 0:
        currentState = titleState(display)
    elif advance_menu == 1:
        currentState = helpState(display)
    elif advance_menu == 2:
        currentState = ingameState(display)
    elif advance_menu == 3:
        currentState = deathState(display)


def advanceEnd(display, score):
    global currentState
    currentState = victoryState(display, score)


def changeColor(newColor):
    global color
    color = newColor


def changeBackground(state):
    global backImage
    backImage = backgroundImages[MENU_ID[state]]
    backImage = pygame.transform.scale(backImage, (WINDOW_SIZE))


def changeBack(number):
    global backImage
    backImage = backgroundImages[number - 1]
    backImage = pygame.transform.scale(backImage, (WINDOW_SIZE))


class Player:
    ACCEL = 4.61
    DECEL = 3.68
    MAX_YVEL = 20
    MAX_XVEL = 8

    WIDTH = 48
    HEIGHT = 32

    def __init__(self):
        self.image, self.rect = loadImage('actions\\playerIdle', 'png')

        self.rightImages = [None, None, None]
        self.leftImages = [None, None, None]
        self.loadImages()
        # movement velocity
        self.xVel = 0
        self.yVel = 0

        # player data
        self.health = 1
        self.score = 0

        # speed of movement
        self.speed = [0, 0]
        self.dx = 0
        self.dy = 0

        # MOVEMENT
        self.up = self.down = self.left = self.right = False

        self.rightCounter = 0
        self.leftCounter = 0
        # direction facing(even if not moving)
        # -1: LEFT, 1: RIGHT
        self.dir = 1

        # shells
        self.shellCount = 1
        self.shell = None
        self.shells = []

        # tests
        self.isFalling = True
        # POSSIBLE
        # self.jumpCounter = 0

    def loadImages(self):
        self.rightImages[0], rect = loadImage('actions\\playerIdle', 'png')
        self.rightImages[1], rect = loadImage('actions\\playerLeftStep', 'png')
        self.rightImages[2], rect = loadImage('actions\\playerRightStep', 'png')

        self.leftImages[0] = pygame.transform.flip(self.rightImages[0], True, False)
        self.leftImages[1] = pygame.transform.flip(self.rightImages[1], True, False)
        self.leftImages[2] = pygame.transform.flip(self.rightImages[2], True, False)

    def update(self, walls):
        if self.xVel > Player.DECEL:
            self.xVel -= Player.DECEL
        elif self.xVel < -Player.DECEL:
            self.xVel += Player.DECEL
        if self.right:
            self.xVel += Player.ACCEL
        if self.left:
            self.xVel -= Player.ACCEL
        if self.up:
            self.jump()

        if self.xVel > Player.MAX_XVEL:
            self.xVel = Player.MAX_XVEL
        elif self.xVel < -Player.MAX_XVEL:
            self.xVel = -Player.MAX_XVEL
        elif abs(self.xVel) <= Player.DECEL:
            self.xVel = 0
        self.speed[0] = int(self.xVel)
        if self.speed[0] != 0:
            self.move_single_axis(self.speed[0], 0, walls)

        if self.isFalling:
            self.yVel += GRAVITY
        if self.yVel > Player.MAX_YVEL:
            self.yVel = Player.MAX_YVEL

        self.speed[1] = int(self.yVel)

        if self.speed[1] != 0:
            self.isFalling = True
            self.move_single_axis(0, self.speed[1], walls)

        self.getImage()

    def move_single_axis(self, dx, dy, walls):
        self.rect.move_ip((dx, dy))
        self.isFalling = True
        for wall in walls:
            if self.rect.colliderect(wall.rect):
                if dx > 0:
                    self.rect.right = wall.rect.left
                    self.xVel = 0
                if dx < 0:
                    self.rect.left = wall.rect.right
                    self.xVel = 0
                if dy > 0:
                    self.rect.bottom = wall.rect.top
                    self.yVel = 0
                    self.isFalling = False
                if dy < 0:
                    self.rect.top = wall.rect.bottom
                    self.yVel = 0

    def jump(self):
        if not self.isFalling:
            self.isFalling = True
            self.yVel = -Player.MAX_YVEL

    def checkDoor(self, door):
        if self.rect.colliderect(door.rect):
            return True

    def set_pos(self, new_pos):
        self.rect.x, self.rect.y = new_pos
        self.rect.y = self.rect.y + 16

    def addPoints(self, pointsInc):
        self.score += pointsInc

    def reduceHealth(self, reduc):
        self.health -= reduc

    def spawnShell(self):
        self.shell = Shell(self.rect, self.dir)
        self.shells.append(self.shell)
        self.shellCount -= 1
        return self.shell

    def getImage(self):
        if self.right and self.left:
            pass
        elif self.right:
            self.dir = 1
        elif self.left:
            self.dir = -1
        if self.dir == 1:
            if self.isFalling or (self.right and self.left) or (not self.right and not self.left):
                self.image = self.rightImages[0]
            elif self.right:
                self.rightCounter += 1
                if self.rightCounter // 10 % 2 == 0:
                    self.image = self.rightImages[1]
                else:
                    self.image = self.rightImages[2]
        elif self.dir == -1:
            if self.isFalling or (self.right and self.left) or (not self.right and not self.left):
                self.image = self.leftImages[0]
            elif self.left:
                self.leftCounter += 1
                if self.leftCounter // 10 % 2 == 0:
                    self.image = self.leftImages[1]
                else:
                    self.image = self.leftImages[2]


class Enemy:
    MAX_XVEL = 3
    MAX_YVEL = 6

    def __init__(self, pos, pK):
        self.image, self.rect = loadImage('enemyactions\\enemyRightStep', 'png')

        self.rect.x, self.rect.y = pos

        self.points = pK
        self.dir = 1

        self.isFalling = False
        self.dx = 0
        self.dy = 0
        self.yVel = 0

        self.rightCounter = 0
        self.leftCounter = 0

    def update(self, walls):

        self.move_single_axis(Enemy.MAX_XVEL * self.dir, 0, walls)

        if self.isFalling:
            self.yVel += GRAVITY
            if self.yVel > Enemy.MAX_YVEL:
                self.yVel = Enemy.MAX_YVEL
            self.move_single_axis(0, int(self.yVel), walls)
        self.getImage()

    def move_single_axis(self, dx, dy, walls):
        self.rect.move_ip((dx, dy))
        self.isFalling = True
        for wall in walls:
            if self.rect.colliderect(wall.rect):
                if dx > 0:
                    self.rect.right = wall.rect.left
                    self.dir = self.dir * -1
                if dx < 0:
                    self.rect.left = wall.rect.right
                    self.dir = self.dir * -1
                if dy > 0:
                    self.rect.bottom = wall.rect.top

    def getImage(self):
        if self.dir == 1:
            self.rightCounter += 1
            if self.rightCounter // 10 % 2 == 0:
                self.image = enemyImages[0][2]
            else:
                self.image = enemyImages[0][0]
        elif self.dir == -1:
            self.leftCounter += 1
            if self.leftCounter // 10 % 2 == 0:
                self.image = enemyImages[1][2]
            else:
                self.image = enemyImages[1][0]
        elif self.dir == 0:
            self.image = enemyImages[0][0]


class Level:
    def __init__(self, level):
        self.all_entities = []
        self.level = level
        self.door = None

        self.enemies = []
        self.enemy = None

        self.walls = []
        self.wall = None

        self.gems = []
        self.gem = None

        self.goldenShell = None
        self.goldenShells = []

        self.playerPos = None

    def createLevel(self):
        ref = {1: level1map, 2: level2map, 3: level3map, 4: level4map}
        current_map = ref[self.level]
        x = 0
        y = 0

        for row in current_map:
            for col in row:
                if col == 'G':
                    self.wall = Wall((x, y), "grass")
                    self.walls.append(self.wall)
                    self.all_entities.append(self.wall)
                if col == 'I':
                    self.wall = Wall((x, y), "ice")
                    self.walls.append(self.wall)
                    self.all_entities.append(self.wall)
                if col == 'D':
                    self.wall = Wall((x, y), "dirt")
                    self.walls.append(self.wall)
                    self.all_entities.append(self.wall)
                if col == 'O':
                    self.door = Door((x, y))
                    self.all_entities.append(self.door)
                if col == 'E':
                    self.enemy = Enemy((x, y), 100)
                    self.enemies.append(self.enemy)
                    self.all_entities.append(self.enemy)
                if col == 'C':
                    self.gem = Gem((x, y))
                    self.gems.append(self.gem)
                    self.all_entities.append(self.gem)
                if col == 'S':
                    self.goldenShell = goldShell((x, y))
                    self.goldenShells.append(self.goldenShell)
                    self.all_entities.append(self.goldenShell)
                if col == 'P':
                    self.playerPos = (x, y)
                x += 48
            x = 0
            y += 48


class ingameState:
    WORLD_WIDTH = 3840
    WORLD_HEIGHT = 720

    ALPHA_INC = 15

    def __init__(self, display):
        # for writing on the screen
        if not pygame.font.get_init():
            pygame.font.init()
        if not pygame.mixer.get_init():
            pygame.mixer.init()

        self.deathSound = pygame.mixer.Sound('assets\\sounds\\enemyDeath.wav')
        self.doorOpen = pygame.mixer.Sound('assets\\sounds\\doorOpen.wav')
        self.doorClose = pygame.mixer.Sound('assets\\sounds\\doorClose.wav')

        self.font2 = pygame.font.SysFont('elephant', 30, True)

        self.menuStrings = ['Click anywhere to exit to title',
                            'Press Esc to return to game'
                            ]
        self.menuTextRects = [pygame.Rect(WIN_WIDTH // 2 - self.font2.size(self.menuStrings[0])[0] // 2,
                                          WIN_HEIGHT // 2 - self.font2.get_height(),
                                          self.font2.size(self.menuStrings[0])[0] // 2,
                                          self.font2.get_height()),
                              pygame.Rect(WIN_WIDTH // 2 - self.font2.size(self.menuStrings[1])[0] // 2,
                                          WIN_HEIGHT // 2 + self.font2.get_height(),
                                          self.font2.size(self.menuStrings[1])[0] // 2,
                                          self.font2.get_height())
                              ]
        self.menuRenderTexts = [self.font2.render(self.menuStrings[0], 1, WHITE),
                                self.font2.render(self.menuStrings[1], 1, WHITE)
                                ]

        self.screen = display

        self.menu_active = False
        self.menu = pygame.Surface(WINDOW_SIZE)
        self.menu.set_alpha(128)
        self.menu.fill((0, 0, 0))

        self.death_screen = False
        self.death = pygame.Surface(WINDOW_SIZE)
        self.death.set_alpha(0)
        self.death.fill((0, 0, 0))

        self.isTransitioning = True
        self.loading = pygame.image.load("assets\\loadImage.png").convert_alpha()
        self.loading = pygame.transform.scale(self.loading, WINDOW_SIZE)
        self.transitionCounter = 0

        # for use in the death screen
        self.alpha = 0

        # SPRITES
        self.player = None
        self.enemies = []
        self.walls = []
        self.door = None
        self.rabbits_left = 0
        self.shell = None
        self.gems = []
        self.goldenShell = None
        self.goldenShells = []

        self.all_entities = []

        # TOP DATA
        self.font = pygame.font.SysFont('elephant', 24, True)

        self.strings = ['Health: 1', 'Level 1:1', 'Score: 0']
        self.textRects = []
        self.textRects.append(pygame.Rect(10, 20, self.font.size(self.strings[0])[0], self.font.get_height()))
        self.textRects.append(pygame.Rect(WIN_WIDTH / 2 - self.font.size(self.strings[1])[0] // 2 - 35, 20,
                                          self.font.size(self.strings[0])[0], self.font.get_height()))
        self.textRects.append(
            pygame.Rect(WIN_WIDTH - self.font.size(self.strings[1])[0] - 70, 20, self.font.size(self.strings[0])[0],
                        self.font.get_height()))
        self.renderTexts = [None, None, None]

        # CHANGE LEVEL
        self.level = None
        self.currentLevel = 1
        self.changeLevel(self.currentLevel)
        self.updateStrings()

        # GAME OBJECTS
        self.camera = complexCamera(self.screen, self.player, (ingameState.WORLD_WIDTH, ingameState.WORLD_HEIGHT))

        changeColor(SKYBLUE)

    def update(self):

        if not self.menu_active and not self.death_screen and not self.isTransitioning:
            for enemy in self.enemies:
                enemy.update(self.walls)
            self.player.update(self.walls)
            for shell in self.player.shells:
                shell.update(self.walls)

            self.checkCollisions()

            self.camera.update()
        if self.death_screen:
            self.alpha += 1
            self.death.set_alpha(self.alpha)
            if self.alpha == 255:
                advanceMenu('DEATH', self.screen)
        if self.isTransitioning:
            self.transitionCounter += 1
            if self.transitionCounter == 160:
                self.transitionCounter = 0
                self.isTransitioning = False
            elif self.transitionCounter == 110:
                self.closeDoor()

    def draw(self):
        self.camera.draw_entities(self.screen, self.all_entities)
        for i in range(len(self.strings)):
            if self.renderTexts[i] is not None:
                self.screen.blit(self.renderTexts[i], self.textRects[i])
        if self.menu_active:
            self.screen.blit(self.menu, (0, 0))
            for i in range(len(self.menuStrings)):
                self.screen.blit(self.menuRenderTexts[i], self.menuTextRects[i])
        if self.death_screen:
            self.screen.blit(self.death, (0, 0))
        if self.isTransitioning:
            self.screen.blit(self.loading, (0, 0))

    def handleInput(self, event):
        if self.death_screen:
            if event.type == pygame.KEYUP:
                if event.key == K_SPACE and self.alpha < (255 - ingameState.ALPHA_INC):
                    self.alpha += ingameState.ALPHA_INC
                    self.death.set_alpha(self.alpha)
                elif event.key == K_SPACE and self.alpha >= (255 - ingameState.ALPHA_INC):
                    self.alpha = 255
                    self.death.set_alpha(self.alpha)
                    advanceMenu('DEATH', self.screen)
        elif self.menu_active:
            if event.type == KEYUP:
                if event.key == K_ESCAPE:
                    self.menu_active = False
            if event.type == MOUSEBUTTONUP:
                advanceMenu('TITLE', self.screen)
        else:
            if event.type == KEYDOWN:
                if event.key == K_UP or event.key == K_SPACE or event.key == K_w:
                    self.player.up = True
                if event.key == K_DOWN or event.key == K_s:
                    self.player.down = True
                if event.key == K_LEFT or event.key == K_a:
                    self.player.left = True
                if event.key == K_RIGHT or event.key == K_d:
                    self.player.right = True
            if event.type == KEYUP:
                if event.key == K_UP or event.key == K_SPACE or event.key == K_w:
                    self.player.up = False
                if event.key == K_DOWN or event.key == K_s:
                    self.player.down = False
                if event.key == K_LEFT or event.key == K_a:
                    self.player.left = False
                if event.key == K_RIGHT or event.key == K_d:
                    self.player.right = False
                if event.key == K_p:
                    if self.player.shellCount >= 1:
                        self.shell = self.player.spawnShell()
                        self.all_entities.append(self.shell)
                if event.key == K_o:
                    for shell in self.player.shells:
                        shell.stopAndJump()
                if event.key == K_h:
                    if self.player.checkDoor(self.door):
                        if self.currentLevel == 4:
                            advanceEnd(self.screen, self.player.score)
                        else:
                            self.changeLevel(self.currentLevel + 1)
                            self.openDoor()
                            self.isTransitioning = True
                if event.key == K_ESCAPE:
                    self.menu_active = True

    def checkCollisions(self):
        if self.player.rect.top > ingameState.WORLD_HEIGHT:
            self.player.reduceHealth(1)
            self.updateStrings()
            self.death_screen = True
        for shell in self.player.shells:
            if shell.rect.top > ingameState.WORLD_HEIGHT:
                self.player.shells.remove(shell)
                self.all_entities.remove(shell)
                self.player.shellCount += 1

        ##PLAYER VS ENEMY and ENEMY VS SHELL and #ENEMIES VS WALL
        for enemy in self.enemies:
            if self.player.rect.colliderect(enemy.rect):
                self.player.reduceHealth(1)
                self.updateStrings()
                self.death_screen = True

        for gem in self.gems:
            if self.player.rect.colliderect(gem.rect):
                self.player.addPoints(Gem.POINTS)
                self.gems.remove(gem)
                self.all_entities.remove(gem)
                self.updateStrings()

        for gShell in self.goldenShells:
            if self.player.rect.colliderect(gShell.rect):
                self.player.addPoints(goldShell.POINTS)
                self.goldenShells.remove(gShell)
                self.all_entities.remove(gShell)
                self.updateStrings()

        for enemy in self.enemies:
            for shell in self.player.shells:
                if shell.rect.colliderect(enemy.rect) and shell.isMoving:
                    self.player.addPoints(enemy.points)
                    shell.stopAndJump()
                    self.rabbitDeath()
                    self.enemies.remove(enemy)
                    self.all_entities.remove(enemy)
                    self.updateStrings()

        for shell in self.player.shells:
            if self.player.rect.colliderect(shell.rect) and not shell.isMoving:
                self.player.shells.remove(shell)
                self.all_entities.remove(shell)
                self.player.shellCount += 1
                self.player.addPoints(50)
                self.updateStrings()

    def changeLevel(self, newLevel):

        self.enemies = []
        self.walls = []
        self.gems = []
        self.level = Level(newLevel)
        self.level.createLevel()

        self.currentLevel = newLevel

        self.enemies = self.level.enemies
        self.walls = self.level.walls
        self.gems = self.level.gems
        self.door = self.level.door
        self.goldenShells = self.level.goldenShells

        self.all_entities = self.level.all_entities
        if type(self.player) == type(None):
            self.player = Player()
        if self.player.shellCount < 1:
            self.player.shellCount += 1
        self.player.shells = []
        self.all_entities.append(self.player)
        self.player.set_pos(self.level.playerPos)
        changeBack(self.currentLevel)
        self.updateStrings()

    def updateStrings(self):
        self.strings = []
        self.strings.append('Lives left: %d' % self.player.health)
        self.strings.append('LEVEL %d:%d' % (1, self.currentLevel))
        self.strings.append('Score: %d' % self.player.score)

        for i in range(len(self.strings)):
            self.renderTexts[i] = self.font.render(self.strings[i], 1, (235, 235, 235))

    def rabbitDeath(self):
        self.deathSound.play()

    def openDoor(self):
        self.doorOpen.play()

    def closeDoor(self):
        self.doorClose.play()


class Wall:
    def __init__(self, pos, name):
        self.image, self.rect = self.getImage(name)
        self.rect.x, self.rect.y = pos

    def update(self):
        pass

    def getImage(self, name):
        if name == "grass":
            return tileImages[0], tileImages[0].get_rect()
        elif name == "dirt":
            return tileImages[1], tileImages[1].get_rect()
        elif name == "ice":
            return tileImages[2], tileImages[2].get_rect()


class Door:
    WIDTH = 96
    HEIGHT = 96

    def __init__(self, pos):
        self.image, self.rect = loadImage('tiles\\door', 'png')
        self.rect.x, self.rect.y = pos

    def update(self):
        pass


class complexCamera:
    def __init__(self, screen, player, LEVELDIM):
        self.player = player
        self.rect = screen.get_rect()
        self.rect.x = self.player.rect.x
        self.world_rect = pygame.Rect((0, 0), LEVELDIM)

    def update(self):
        if self.player.rect.centerx < self.rect.w // 2:
            self.rect.centerx = self.rect.w // 2
        elif self.player.rect.centerx > self.world_rect.w - self.rect.w // 2:
            self.rect.centerx = self.world_rect.w - self.rect.w // 2
        else:
            self.rect.centerx = self.player.rect.centerx

    def draw_entities(self, display, entities):
        for e in entities:
            if e.rect.colliderect(self.rect):
                display.blit(e.image, RelRect(e, self))

    def reset(self):
        self.rect.centerx = self.world_rect.w // 2


class Shell:
    MAX_XVEL = 12
    MAX_YVEL = 6
    STOPVEL = 0

    WIDTH = 48
    HEIGHT = 32

    def __init__(self, player_rect, direction):
        self.image, self.rect = loadImage('shell', 'png')
        self.rect.topleft = player_rect.topleft

        self.dir = direction
        self.isFalling = False
        self.yVel = 0
        self.isMoving = True

        self.counter = 0

    def update(self, walls):
        self.isMoving = False
        if self.dir != 0:
            self.isMoving = True
        self.move_single_axis(Shell.MAX_XVEL * self.dir, 0, walls)
        ## -1 if left, 0 if stopped, 1 if right
        if self.isFalling:
            self.yVel += GRAVITY
            if self.yVel > Shell.MAX_YVEL:
                self.yVel = Shell.MAX_YVEL

        self.move_single_axis(0, int(self.yVel), walls)

        if self.dir != 0:
            self.counter += 1
        if self.counter % 8 == 0:
            self.image = pygame.transform.flip(self.image, True, False)

    def stopAndJump(self):
        self.dir = 0
        self.yVel = -Shell.MAX_YVEL * 2

    def move_single_axis(self, dx, dy, walls):
        self.rect.move_ip((dx, dy))
        self.isFalling = True
        for wall in walls:
            if self.rect.colliderect(wall.rect):
                if dx > 0:
                    self.rect.right = wall.rect.left
                    self.dir = -1
                if dx < 0:
                    self.rect.left = wall.rect.right
                    self.dir = 1
                if dy > 0:
                    self.rect.bottom = wall.rect.top
                    self.yVel = 0
                    self.isFalling = False
                if dy < 0:
                    self.rect.top = wall.rect.bottom
                    self.yVel = 0


class Gem:
    POINTS = 100

    def __init__(self, pos):
        self.image, self.rect = loadImage('gem', 'png')
        self.rect.topleft = pos


class goldShell:
    POINTS = 2000

    def __init__(self, pos):
        self.image, self.rect = loadImage('goldShell', 'png')
        self.image = pygame.transform.scale(self.image, (48, 32))
        self.rect.x, self.rect.y = pos
        self.rect.y = self.rect.y + 16


class titleState:
    cButtonHighlight = (0, 255, 0, 100)
    cButtonColor = (255, 255, 255)

    # buttons to advance to the next screen
    buttonHighlight = (0, 200, 0, 100)
    buttonColor = (200, 200, 200)

    HORIZ_RADIUS = 80
    VERT_RADIUS = 30

    game_string = 'Tucker the Turtle!'

    start_string = 'START'
    help_string = 'HELP'
    quit_string = 'QUIT'

    BUTTON_ID = {start_string: 0, help_string: 1, quit_string: 2}

    def __init__(self, display):
        if not pygame.font.get_init():
            pygame.font.init()
        self.font = pygame.font.SysFont("century", 30)
        self.title_font = pygame.font.SysFont("elephant", 80)

        self.doubleClickTimer = 0
        changeColor(SKYBLUE)
        changeBackground("TITLE")
        self.screen = display

        # easy access of locations
        self.origins_of_buttons = [WIN_WIDTH / 2 - titleState.HORIZ_RADIUS, WIN_WIDTH / 2 + titleState.HORIZ_RADIUS,
                                   WIN_HEIGHT / 2, WIN_HEIGHT / 2 + 3 * titleState.VERT_RADIUS,
                                   WIN_HEIGHT / 2 + 6 * titleState.VERT_RADIUS]

        # buttons
        self.startButton = pygame.Rect(self.origins_of_buttons[0], self.origins_of_buttons[2],
                                       self.origins_of_buttons[1] - self.origins_of_buttons[0],
                                       self.origins_of_buttons[2] + 2 * titleState.VERT_RADIUS -
                                       self.origins_of_buttons[2])
        self.helpButton = pygame.Rect(self.origins_of_buttons[0], self.origins_of_buttons[3],
                                      self.origins_of_buttons[1] - self.origins_of_buttons[0],
                                      self.origins_of_buttons[3] + 2 * titleState.VERT_RADIUS - self.origins_of_buttons[
                                          3])
        self.quitButton = pygame.Rect(self.origins_of_buttons[0], self.origins_of_buttons[4],
                                      self.origins_of_buttons[1] - self.origins_of_buttons[0],
                                      self.origins_of_buttons[4] + 2 * titleState.VERT_RADIUS - self.origins_of_buttons[
                                          4])

        self.titleButtons = (self.startButton, self.helpButton, self.quitButton)

        # texts
        self.titleText = pygame.Rect(WIN_WIDTH / 2 - self.title_font.size(titleState.game_string)[0] / 2,
                                     WIN_HEIGHT / 4 - self.title_font.get_height() / 2,
                                     self.title_font.size(titleState.game_string)[0], self.title_font.get_height())

        self.startText = pygame.Rect(WIN_WIDTH / 2 - self.font.size(titleState.start_string)[0] / 2,
                                     CENTER[1] + titleState.VERT_RADIUS - self.font.get_height() / 2,
                                     self.font.size(titleState.start_string)[0], self.font.get_height())
        self.helpText = pygame.Rect(WIN_WIDTH / 2 - self.font.size(titleState.help_string)[0] / 2,
                                    CENTER[1] + 4 * titleState.VERT_RADIUS - self.font.get_height() / 2,
                                    self.font.size(titleState.help_string)[0], self.font.get_height())
        self.quitText = pygame.Rect(WIN_WIDTH / 2 - self.font.size(titleState.quit_string)[0] / 2,
                                    CENTER[1] + 7 * titleState.VERT_RADIUS - self.font.get_height() / 2,
                                    self.font.size(titleState.quit_string)[0], self.font.get_height())

        self.textRect = (self.startText, self.helpText, self.quitText)

        # click and double click
        self.buttonClicked = [False, False, False]
        self.buttonDoubleClick_Active = [False, False, False]

        # titletext
        self.titleT = self.title_font.render(titleState.game_string, 1, titleState.buttonHighlight)

        # textHighlight
        self.startTH = self.font.render(titleState.start_string, 1, titleState.cButtonHighlight)
        self.helpTH = self.font.render(titleState.help_string, 1, titleState.cButtonHighlight)
        self.quitTH = self.font.render(titleState.quit_string, 1, titleState.cButtonHighlight)

        self.textHighlight = (self.startTH, self.helpTH, self.quitTH)

        # regularText
        self.startT = self.font.render(titleState.start_string, 1, titleState.buttonHighlight)
        self.helpT = self.font.render(titleState.help_string, 1, titleState.buttonHighlight)
        self.quitT = self.font.render(titleState.quit_string, 1, titleState.buttonHighlight)

        self.text = (self.startT, self.helpT, self.quitT)

    def update(self):
        self.doubleClickTimer += 1
        for i in range(len(self.buttonDoubleClick_Active)):
            if (self.buttonDoubleClick_Active[i] and self.doubleClickTimer > 20):
                self.buttonDoubleClick_Active[i] = False

    def draw(self):
        self.screen.blit(self.titleT, self.titleText)
        for i in range(len(self.titleButtons)):
            if (self.buttonClicked[i]):
                self.screen.fill(self.cButtonColor, self.titleButtons[i])
                pygame.draw.rect(self.screen, self.cButtonHighlight, self.titleButtons[i], 5)
                self.screen.blit(self.textHighlight[i], self.textRect[i])
            else:
                self.screen.fill(self.buttonColor, self.titleButtons[i])
                pygame.draw.rect(self.screen, self.buttonHighlight, self.titleButtons[i], 5)
                self.screen.blit(self.text[i], self.textRect[i])

    def handleInput(self, event):
        if event.type == pygame.KEYDOWN:
            if event.key == K_RETURN:
                for i in range(len(self.titleButtons)):
                    if (self.buttonClicked[i]):
                        if (i == 2):
                            pygame.quit()
                            sys.exit(0)
                        elif (i == 0):
                            advanceMenu('INGAME', self.screen)
                        elif (i == 1):
                            advanceMenu('HELP', self.screen)
        if event.type == pygame.MOUSEBUTTONDOWN:
            mouse = pygame.mouse.get_pos()
            for i in range(len(self.titleButtons)):
                if (mouse[0] > self.titleButtons[i].left and mouse[0] < self.titleButtons[i].right):
                    if (mouse[1] > self.titleButtons[i].top and mouse[1] < self.titleButtons[i].bottom):
                        self.setButtons(len(self.titleButtons), 'UNCLICKED')
                        self.setButtons(i, 'CLICKED')

    # helper method
    def setButtons(self, button, state):
        if (button > 2):
            for i in range(button):
                self.buttonClicked[i] = False
        else:
            self.buttonClicked[button] = True
            if (self.buttonDoubleClick_Active[button]):
                if (button == self.BUTTON_ID[titleState.quit_string]):
                    pygame.quit()
                elif (button == self.BUTTON_ID[titleState.start_string]):
                    advanceMenu('INGAME', self.screen)
                elif (button == self.BUTTON_ID[titleState.help_string]):
                    advanceMenu('HELP', self.screen)
            else:
                self.doubleClickTimer = 0
                for i in range(len(self.buttonDoubleClick_Active)):
                    self.buttonDoubleClick_Active[i] = False
                self.buttonDoubleClick_Active[button] = True


class helpState:
    stringColor = (0, 233, 0)

    def __init__(self, display):
        if not pygame.font.get_init():
            pygame.font.init()
        changeColor((255, 255, 255))
        changeBackground("HELP")
        self.screen = display

        self.font = pygame.font.SysFont('century', 36, True)

        self.strings = ['To jump: Press W, the spacebar, or the up arrow key',
                        'To move left: hold A or the left arrow key',
                        'To move right: hold D or the right arrow key',
                        'To spawn a shell: Press P',
                        'To enter a door: Press H',
                        'If you need to stop a shell: press O',
                        'To pause the game: Press Esc'
                        ]
        self.backString = 'Back'
        self.textRects = []
        for i in range(len(self.strings)):
            self.textRects.append(pygame.Rect(WIN_WIDTH / 2 - self.font.size(self.strings[i])[0] // 2,
                                              CENTER[1] - 200 + 70 * i - self.font.get_height() // 2,
                                              self.font.size(self.strings[i])[0],
                                              self.font.get_height()))
        self.textRects.append(
            pygame.Rect(52.5 - self.font.size(self.backString)[0] // 2, 690 - self.font.get_height() // 2,
                        self.font.size(self.backString)[0],
                        self.font.get_height()))

        self.renderTexts = []
        for i in range(len(self.strings)):
            self.renderTexts.append(self.font.render(self.strings[i], 1, helpState.stringColor))
        self.renderTexts.append(self.font.render(self.backString, 1, helpState.stringColor))

    def update(self):
        pass

    def draw(self):
        for i in range(len(self.renderTexts)):
            pygame.draw.rect(self.screen, (100, 100, 100), self.textRects[i])
            self.screen.blit(self.renderTexts[i], self.textRects[i])
        pygame.draw.rect(self.screen, GRAY, pygame.Rect(-10, 660, 120, 70), 8)

    def handleInput(self, event):
        if event.type == pygame.MOUSEBUTTONDOWN:
            mouse = pygame.mouse.get_pos()
            if (mouse[0] > self.textRects[7].left and mouse[0] < self.textRects[7].right):
                if (mouse[1] > self.textRects[7].top and mouse[1] < self.textRects[7].bottom):
                    advanceMenu('TITLE', self.screen)


class deathState:
    def __init__(self, display):
        if not pygame.font.get_init():
            pygame.font.init()
        self.font = pygame.font.SysFont('elephant', 30, True)
        changeColor((0, 0, 0))
        self.screen = display

        self.strings = ['You died!',
                        'Click anywhere to exit'
                        ]
        self.textRects = [pygame.Rect(WIN_WIDTH // 2 - self.font.size(self.strings[0])[0] // 2,
                                      WIN_HEIGHT // 2 - self.font.get_height(),
                                      self.font.size(self.strings[0])[0],
                                      self.font.get_height()),
                          pygame.Rect(WIN_WIDTH // 2 - self.font.size(self.strings[1])[0] // 2,
                                      WIN_HEIGHT // 2 + self.font.get_height(),
                                      self.font.size(self.strings[1])[0],
                                      self.font.get_height())
                          ]
        self.renderTexts = [self.font.render(self.strings[0], 1, WHITE),
                            self.font.render(self.strings[1], 1, WHITE)]

        self.render_alpha = 255
        self.alpha_dir = -1

    def update(self):
        self.render_alpha = self.render_alpha + 15 * self.alpha_dir
        if self.render_alpha == 0 or self.render_alpha == 255:
            self.alpha_dir *= -1
        self.renderTexts[1].set_alpha(self.render_alpha)

    def draw(self):
        self.screen.fill(BLACK)
        for i in range(len(self.strings)):
            self.screen.blit(self.renderTexts[i], self.textRects[i])

    def handleInput(self, event):
        if event.type == MOUSEBUTTONUP:
            advanceMenu('TITLE', self.screen)


class victoryState:
    def __init__(self, display, score):
        if not pygame.font.get_init():
            pygame.font.init()
        self.font = pygame.font.SysFont('elephant', 30, True)
        changeColor((0, 0, 0))
        self.screen = display

        self.strings = ['You won!',
                        'Click anywhere to exit',
                        'Score: %d' % score
                        ]
        self.textRects = [pygame.Rect(WIN_WIDTH // 2 - self.font.size(self.strings[0])[0] // 2,
                                      WIN_HEIGHT // 2 - self.font.get_height(),
                                      self.font.size(self.strings[0])[0],
                                      self.font.get_height()),
                          pygame.Rect(WIN_WIDTH // 2 - self.font.size(self.strings[1])[0] // 2,
                                      WIN_HEIGHT // 2 + self.font.get_height(),
                                      self.font.size(self.strings[1])[0],
                                      self.font.get_height()),
                          pygame.Rect(WIN_WIDTH // 2 - self.font.size(self.strings[2])[0] // 2,
                                      WIN_HEIGHT // 2 + 3 * self.font.get_height(),
                                      self.font.size(self.strings[2])[0],
                                      self.font.get_height())
                          ]
        self.renderTexts = [self.font.render(self.strings[0], 1, WHITE),
                            self.font.render(self.strings[1], 1, WHITE),
                            self.font.render(self.strings[2], 1, WHITE)]

    def update(self):
        pass

    def draw(self):
        self.screen.fill(BLACK)
        for i in range(len(self.strings)):
            self.screen.blit(self.renderTexts[i], self.textRects[i])

    def handleInput(self, event):
        if event.type == MOUSEBUTTONUP:
            advanceMenu('TITLE', self.screen)


# GAME IMAGES
# imgs = [None] #TBD

# QUICK REFERENCE PLAYER START POSITIONS
playerStartPos = ((64, 336), (48, 336), (48, 336), (48, 336))

# BACKGROUND NEUTRAL COLOR
color = 0, 0, 0
backgroundImages = [None, None, None, None]
enemyImages = [[None, None, None], [None, None, None]]
tileImages = [None, None, None]

currentState = None
backImage = None

# initialize pygame
os.environ["SDL_VIDEO_CENTERED"] = "1"
pygame.init()
pygame.mixer.init()

# GAME SCREEN
WINDOW_SIZE = WIN_WIDTH, WIN_HEIGHT = 1280, 720
WINDOW = pygame.Rect((0, 0), WINDOW_SIZE)

CENTER = (WIN_WIDTH / 2, WIN_HEIGHT / 2)

screen = pygame.display.set_mode(WINDOW_SIZE)
pygame.display.set_caption('Tucker the Turtle')

loadImages()
# STARTING MENU
advanceMenu('TITLE', screen)

# MAIN LOOP
while 1:
    clock = pygame.time.Clock()
    clock.tick(FPS)
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit(0)
        currentState.handleInput(event)

    currentState.update()
    drawBackground(screen)
    currentState.draw()

    pygame.display.flip()