package me.mintdev.exceptions

import me.mintdev.models.Image

class AlreadyRenamedException (val image: Image): Exception()
